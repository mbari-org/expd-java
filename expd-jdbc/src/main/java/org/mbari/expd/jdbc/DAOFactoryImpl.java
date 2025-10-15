/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd.jdbc;
import org.mbari.expd.CameraDatumDAO;
import org.mbari.expd.CtdDatumDAO;
import org.mbari.expd.DAOFactory;
import org.mbari.expd.DiveDAO;
import org.mbari.expd.ExpeditionDAO;
import org.mbari.expd.NavigationDatumDAO;
import org.mbari.expd.UberDatumDAO;

/**
 * Factory for creating DAOs. It can be manually constructed with the required JDBC parameters
 * or it can be constructed from an {@link AppConfig}. The latter is will read the JDBC parameters
 * from the following environment variables:
 *  - EXPD_JDBC_URL
 *  - EXPD_JDBC_USERNAME
 *  - EXPD_JDBC_PASSWORD
 *  - EXPD_JDBC_DRIVER (optional)
 *
 * @author brian
 */
public class DAOFactoryImpl implements DAOFactory {

    private final JdbcParameters jdbcParameters;

    public DAOFactoryImpl(JdbcParameters jdbcParameters) {
        this.jdbcParameters = jdbcParameters;
    }

    public DAOFactoryImpl() {
        this(AppConfig.fromEnv());
    }

    @Override
    public CameraDatumDAO newCameraDatumDAO() {
        return new CameraDatumDAOImpl(jdbcParameters);
    }

    @Override
    public CtdDatumDAO newCtdDatumDAO() {
        return new CtdDatumDAOImpl(jdbcParameters);
    }

    @Override
    public DiveDAO newDiveDAO() {
        return new DiveDAOImpl(jdbcParameters);
    }

    @Override
    public ExpeditionDAO newExpeditionDAO() {
        return new ExpeditionDAOImpl(jdbcParameters);
    }

    @Override
    public NavigationDatumDAO newNavigationDatumDAO() {
        return new NavigationDatumDAOImpl(jdbcParameters);
    }

    @Override
    public UberDatumDAO newUberDatumDAO() {
       return new UberDatumDAOImpl(newCameraDatumDAO(), newNavigationDatumDAO(), newCtdDatumDAO());
    }

}
