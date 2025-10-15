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

    /**
     * Constructs a new DAO factory with the given JDBC parameters
     * @param jdbcParameters The JDBC parameters for creating and managing the database connection.
     */
    public DAOFactoryImpl(JdbcParameters jdbcParameters) {
        this.jdbcParameters = jdbcParameters;
    }

    /**
     * Constructs a new DAO factory with the JDBC parameters read from the environment variables
     * defined in {@link AppConfig}.
     */
    public DAOFactoryImpl() {
        this(AppConfig.fromEnv());
    }

    /**
     * Creates a new {@link CameraDatumDAO} instance.
     * @return The new instance
     */
    @Override
    public CameraDatumDAO newCameraDatumDAO() {
        return new CameraDatumDAOImpl(jdbcParameters);
    }

    /**
     * Creates a new {@link CtdDatumDAO} instance.
     * @return The new instance
     */
    @Override
    public CtdDatumDAO newCtdDatumDAO() {
        return new CtdDatumDAOImpl(jdbcParameters);
    }

    /**
     * Creates a new {@link DiveDAO} instance.
     * @return The new instance
     */
    @Override
    public DiveDAO newDiveDAO() {
        return new DiveDAOImpl(jdbcParameters);
    }

    /**
     * Creates a new {@link ExpeditionDAO} instance.
     * @return The new instance
     */
    @Override
    public ExpeditionDAO newExpeditionDAO() {
        return new ExpeditionDAOImpl(jdbcParameters);
    }

    /**
     * Creates a new {@link NavigationDatumDAO} instance.
     * @return The new instance
     */
    @Override
    public NavigationDatumDAO newNavigationDatumDAO() {
        return new NavigationDatumDAOImpl(jdbcParameters);
    }

    /**
     * Creates a new {@link UberDatumDAO} instance.
     * @return The new instance
     */
    @Override
    public UberDatumDAO newUberDatumDAO() {
       return new UberDatumDAOImpl(newCameraDatumDAO(), newNavigationDatumDAO(), newCtdDatumDAO());
    }

}
