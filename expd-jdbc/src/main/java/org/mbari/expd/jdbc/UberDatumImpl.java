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
    
    /**
     * Creates an empty UberDatumImpl with all components unset.
     */
    public UberDatumImpl() {
    }

    /**
     * Creates a new UberDatumImpl with the provided component data objects.
     * @param cameraDatum the camera data component
     * @param navigationDatum the navigation data component
     * @param ctdDatum the CTD data component
     */
    public UberDatumImpl(CameraDatum cameraDatum, NavigationDatum navigationDatum, CtdDatum ctdDatum) {
        this.cameraDatum = cameraDatum;
        this.navigationDatum = navigationDatum;
        this.ctdDatum = ctdDatum;
    }

    /**
     * Returns the camera data component.
     * @return the CameraDatum, or null if not set
     */
    @Override
    public CameraDatum getCameraDatum() {
        return cameraDatum;
    }

    /**
     * Sets the camera data component.
     * @param cameraDatum the CameraDatum to set
     */
    @Override
    public void setCameraDatum(CameraDatum cameraDatum) {
        this.cameraDatum = cameraDatum;
    }

    /**
     * Returns the CTD data component.
     * @return the CtdDatum, or null if not set
     */
    @Override
    public CtdDatum getCtdDatum() {
        return ctdDatum;
    }

    /**
     * Sets the CTD data component.
     * @param ctdDatum the CtdDatum to set
     */
    @Override
    public void setCtdDatum(CtdDatum ctdDatum) {
        this.ctdDatum = ctdDatum;
    }

    /**
     * Returns the navigation data component.
     * @return the NavigationDatum, or null if not set
     */
    @Override
    public NavigationDatum getNavigationDatum() {
        return navigationDatum;
    }

    /**
     * Sets the navigation data component.
     * @param navigationDatum the NavigationDatum to set
     */
    @Override
    public void setNavigationDatum(NavigationDatum navigationDatum) {
        this.navigationDatum = navigationDatum;
    }

    /**
     * Compares this object to the specified object for equality based on its components.
     * @param obj the object to compare
     * @return true if equal; false otherwise
     */
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

    /**
     * Returns a hash code based on the component objects.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.cameraDatum != null ? this.cameraDatum.hashCode() : 0);
        hash = 59 * hash + (this.navigationDatum != null ? this.navigationDatum.hashCode() : 0);
        hash = 59 * hash + (this.ctdDatum != null ? this.ctdDatum.hashCode() : 0);
        return hash;
    }

    /**
     * Compares this object with the specified {@link UberDatum} for order using camera dates.
     * @param o the UberDatum to compare to
     * @return a negative integer, zero, or a positive integer as this object is less than,
     *         equal to, or greater than the specified object
     */
    @Override
    public int compareTo(UberDatum o) {
        return cameraDatum.getDate().compareTo(o.getCameraDatum().getDate());
    }


    /**
     * Returns a human-readable representation that includes component dates.
     * @return the string representation
     */
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
