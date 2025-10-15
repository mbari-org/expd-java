/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd.jdbc;

import org.mbari.expd.CameraDatum;
import org.mbari.expd.CtdDatum;
import org.mbari.expd.NavigationDatum;
import org.mbari.expd.UberDatum;

import java.util.Date;

/**
 * Implementation of the {@link UberDatum} interface that encapsulates data from
 * multiple sources such as Camera, Navigation, and CTD systems. This class also
 * implements the {@link Comparable} interface, allowing instances to be compared
 * based on the date associated with the CameraDatum.
 *
 * This class serves as a container to manage and provide access to data elements
 * encapsulated in {@link CameraDatum}, {@link NavigationDatum}, and {@link CtdDatum}.
 * It includes methods for setting and retrieving the values of these components.
 */
public class UberDatumImpl implements UberDatum, Comparable<UberDatum> {

    private CameraDatum cameraDatum;
    private NavigationDatum navigationDatum;
    private CtdDatum ctdDatum;
    
    public UberDatumImpl() {
    }

    public UberDatumImpl(CameraDatum cameraDatum, NavigationDatum navigationDatum, CtdDatum ctdDatum) {
        this.cameraDatum = cameraDatum;
        this.navigationDatum = navigationDatum;
        this.ctdDatum = ctdDatum;
    }

    @Override
    public CameraDatum getCameraDatum() {
        return cameraDatum;
    }

    @Override
    public void setCameraDatum(CameraDatum cameraDatum) {
        this.cameraDatum = cameraDatum;
    }

    @Override
    public CtdDatum getCtdDatum() {
        return ctdDatum;
    }

    @Override
    public void setCtdDatum(CtdDatum ctdDatum) {
        this.ctdDatum = ctdDatum;
    }

    @Override
    public NavigationDatum getNavigationDatum() {
        return navigationDatum;
    }

    @Override
    public void setNavigationDatum(NavigationDatum navigationDatum) {
        this.navigationDatum = navigationDatum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UberDatumImpl other = (UberDatumImpl) obj;
        if (this.cameraDatum != other.cameraDatum && (this.cameraDatum == null || !this.cameraDatum.equals(other.cameraDatum))) {
            return false;
        }
        if (this.navigationDatum != other.navigationDatum && (this.navigationDatum == null || !this.navigationDatum.equals(other.navigationDatum))) {
            return false;
        }
        if (this.ctdDatum != other.ctdDatum && (this.ctdDatum == null || !this.ctdDatum.equals(other.ctdDatum))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.cameraDatum != null ? this.cameraDatum.hashCode() : 0);
        hash = 59 * hash + (this.navigationDatum != null ? this.navigationDatum.hashCode() : 0);
        hash = 59 * hash + (this.ctdDatum != null ? this.ctdDatum.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(UberDatum o) {
        return cameraDatum.getDate().compareTo(o.getCameraDatum().getDate());
    }


    @Override
    public String toString() {

        String camDate = cameraDatum == null? "No Camera" : "Camera Date: " + dateToString(cameraDatum.getDate());
        String navDate = navigationDatum == null ? "No Navigation" : "Navigation Date: " + dateToString(navigationDatum.getDate());
        String ctdDate = ctdDatum == null? "No CTD" : "CTD Date: " + dateToString(ctdDatum.getDate());

        return "UberDatumImpl{" +
                camDate +
                ", " + navDate +
                ", " + ctdDate +
                '}';
    }

    private String dateToString(Date date) {
        if (date == null) {
            return "MISSING";
        }
        else {
            return date.toInstant().toString();
        }
    }
}
