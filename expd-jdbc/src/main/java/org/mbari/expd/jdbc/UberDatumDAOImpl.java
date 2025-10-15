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
 *
 * @author brian
 */
public class UberDatumDAOImpl implements UberDatumDAO {

    private final CameraDatumDAO cameraDatumDAO;
    private final NavigationDatumDAO navigationDatumDAO;
    private final CtdDatumDAO ctdDatumDAO;
    
    private static final Logger log = System.getLogger(UberDatumDAOImpl.class.getName());

    public UberDatumDAOImpl(CameraDatumDAO cameraDatumDAO, NavigationDatumDAO navigationDatumDAO, CtdDatumDAO ctdDatumDAO) {
        this.cameraDatumDAO = cameraDatumDAO;
        this.navigationDatumDAO = navigationDatumDAO;
        this.ctdDatumDAO = ctdDatumDAO;
    }

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
     * @param dive
     * @param toleranceSec
     * @return
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
