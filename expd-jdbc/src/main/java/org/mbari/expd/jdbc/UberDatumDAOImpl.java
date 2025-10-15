/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd.jdbc;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.mbari.expd.CameraDatum;
import org.mbari.expd.CameraDatumDAO;
import org.mbari.expd.CtdDatum;
import org.mbari.expd.CtdDatumDAO;
import org.mbari.expd.Dive;
import org.mbari.expd.NavigationDatum;
import org.mbari.expd.NavigationDatumDAO;
import org.mbari.expd.UberDatum;
import org.mbari.expd.UberDatumDAO;

/**
 * Implementation of the {@link UberDatumDAO} interface, providing methods to fetch
 * combined data from camera, navigation, and CTD (Conductivity, Temperature, Depth) sources.
 * This DAO is responsible for aggregating data from these multiple sources and organizing it
 * into {@link UberDatum} objects that encapsulate data points from all three sources.
 */
public class UberDatumDAOImpl implements UberDatumDAO {

    private final CameraDatumDAO cameraDatumDAO;
    private final NavigationDatumDAO navigationDatumDAO;
    private final CtdDatumDAO ctdDatumDAO;
    
    private static final Logger log = System.getLogger(UberDatumDAOImpl.class.getName());

    /**
     * Creates a new instance of UberDatumDAOImpl
     * @param cameraDatumDAO The DAO for camera data
     * @param navigationDatumDAO The DAO for navigation data
     * @param ctdDatumDAO The DAO for conductivity, temperature, depth data
     */
    public UberDatumDAOImpl(CameraDatumDAO cameraDatumDAO, NavigationDatumDAO navigationDatumDAO, CtdDatumDAO ctdDatumDAO) {
        this.cameraDatumDAO = cameraDatumDAO;
        this.navigationDatumDAO = navigationDatumDAO;
        this.ctdDatumDAO = ctdDatumDAO;
    }

    /**
     * Fetches a list of {@link UberDatum} objects combining navigation, CTD, and camera data for a specific dive.
     * This method retrieves data from various sources including navigation, camera, and CTD data,
     * and aggregates them into an {@link UberDatum} object for each data point.
     * If no data is found for certain timestamps or errors occur while fetching data, the corresponding
     * {@link UberDatum} fields may be null.
     *
     * @param dive The dive object containing metadata about the dive such as ROV name and dive number.
     * @param isHD Indicates whether to fetch high-definition camera data (true) or standard-definition camera data (false).
     * @param toleranceSec The time tolerance, in seconds, within which data from different datasets will be considered for aggregation.
     * @return A list of {@link UberDatum} objects, where each combines the matching navigation, CTD, and camera data
     * for the same timestamp, or contains null for any missing data.
     */
    @Override
    public List<UberDatum> fetchData(Dive dive, boolean isHD, double toleranceSec) {
        List<UberDatum> data = new ArrayList<>();

        List<NavigationDatum> navigationData = navigationDatumDAO.fetchBestNavigationData(dive);
        if (navigationData == null || navigationData.isEmpty()) {
            log.log(Level.WARNING, "No navigation data was found for: " + dive.getRovName() +
                " #" + dive.getDiveNumber());
            return data;
         }

        // Load navigation data
        List<Date> dates = navigationData.stream()
                    .map(NavigationDatum::getDate)
                    .collect(Collectors.toList());

        // Hopefully this won't fail :-)
        List<CtdDatum> ctdData = Collections.emptyList();
        try {
            ctdData = ctdDatumDAO.fetchCtdData(dive, dates, toleranceSec);
        }
        catch (Exception e) {
            log.log(Level.WARNING, "Failed to fetch ctd data", e);
        }

        // This can fail for MiniROV as there's no camera data tables
        List<CameraDatum> cameraData = Collections.emptyList();
        try {
            cameraData = cameraDatumDAO.fetchCameraData(dive, dates, toleranceSec, isHD);
        }
        catch (Exception e) {
            log.log(Level.WARNING, "Failed to fetch camera data");
        }

        for (int i = 0; i < dates.size(); i++) {
            NavigationDatum nav = navigationData.size() == dates.size() ? navigationData.get(i) : null;
            CtdDatum ctd = ctdData.size() == dates.size() ? ctdData.get(i) : null;
            CameraDatum cam = cameraData.size() == dates.size() ? cameraData.get(i) : null;
            data.add(new UberDatumImpl(cam, nav, ctd));
        }
        return data;
    }

    /**
     * Fetch data (but without cameradata) only nav and ctd, camera is null. All CTD for dive is fetched,
     * but with if no navigation within +/- tolerance is found the navigation will be null.
     *
     * @param dive The dive to fetch data for
     * @param toleranceSec The tolerance in seconds
     * @return A list of UberDatum objects.
     */
    @Override
    public List<UberDatum> fetchCtdData(Dive dive, double toleranceSec) {
        List<CtdDatum> ctdData = ctdDatumDAO.fetchCtdData(dive);
        List<Date> dates = new ArrayList<>(ctdData.size());
        for (CtdDatum ctdDatum : ctdData) {
            dates.add(ctdDatum.getDate());
        }
        List<NavigationDatum> navigationData = navigationDatumDAO.fetchNavigationData(dive, dates, toleranceSec);
        List<UberDatum> data = new ArrayList<>(ctdData.size());

        for (int i = 0; i < dates.size(); i++) {
            data.add(new UberDatumImpl(null, navigationData.get(i), ctdData.get(i)));
        }

        return data;
    }

}
