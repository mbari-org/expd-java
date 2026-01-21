/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mbari.expd.jdbc;

/*-
 * #%L
 * org.mbari.expd:expd-jdbc
 * %%
 * Copyright (C) 2008 - 2026 Monterey Bay Aquarium Research Institute
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
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
