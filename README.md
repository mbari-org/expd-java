# EXPD

EXPD is a Java API for simplifying access to MBARI's expedition database (also called EXPD). It uses JDBC for data access. It also contains conditional logic to allow tasks such as selecting the best available navigation data and oxygen data.

## Adding to your project

This library is available on [Maven Central](https://central.sonatype.com/artifact/org.mbari.expd/expd-jdbc).

```
<dependency>
    <groupId>org.mbari.expd</groupId>
    <artifactId>expd-jdbc</artifactId>
    <version>2.0.0</version>
</dependency>
```

## Usage for developers

You either initialize the DAO with a connection parameters or through environment variables.

```java
import java.util.Optional;
import org.mbari.expd.jdbc.DAOFactoryImpl;
import org.mbari.expd.jdbc.JdbcParameters;

// Using connection parameters
var jdbcParams = new JdbcParameters("jdbc:mysql://localhost:3306/expd", "root", "password", Optional.of("org.mariadb.jdbc.Driver"));
DAOFactory daoFactory = new DAOFactoryImpl(jdbcParams);


// Using environment variables: set these in your shell or IDE run configuration
// export EXPD_JDBC_URL="jdbc:mysql://localhost:3306/expd"
// export EXPD_JDBC_USER="root"
// export EXPD_JDBC_PASSWORD="password"
// export EXPD_JDBC_DRIVER="org.mariadb.jdbc.Driver"
DAOFactory daoFactory = new DAOFactoryImpl(); // will use environment variables

// Use the daoFactory to get DAOs
var diveDAO = daoFactory.getDiveDAO();

```

### Examples 

#### To get a list of all ROVs

```java
import org.mbari.expd.jdbc.BaseDAOImpl;

List<String> rovs = BaseDAOImpl.getAllRovNames();
```

#### To get all dives for a particular ROV:

```java
import org.mbari.expd.DiveDAO;
import org.mbari.expd.jdbc.DiveDAOImpl;

DiveDAO dao = daoFactory.getDiveDAO();

Collection<Dive> divesForRov = dao.findByPlatform(rovNameAsString);


// Example converting them to a list of dive numbers:
List<Integer> diveNumbersForRov = divesForRov.stream()
        .map(Dive::getDiveNumber)
        .sorted()
        .toList();
```

#### To get a dive object by ROV name and dive number

```java
import org.mbari.expd.DiveDAO;
import org.mbari.expd.jdbc.DiveDAOImpl;

DiveDAO dao = daoFactory.getDiveDAO();

// returns null if no match is found
Dive dive = dao.findByPlatformAndDiveNumber("Ventana", 123);
```

### To get other data for a particular dive:

#### Position

```java
// latitude, longitude, depth
import org.mbari.expd.NavigationDatum;
import org.mbari.expd.NavigationDatumDAO;
import org.mbari.expd.jdbc.NavigationDAOImpl;

// You'll need a Dive object 
NavigationDAO dao = daoFactory.getNavigationDAO();
List<NavigationDatum> nav = dao.fetchBestnavigationData(dive);
```

#### CTD

```java
// salinity (psu), temperature (celsius), pressure (dbar)
import org.mbari.expd.CtdDatum;
import org.mbari.expd.CtdDatumDAO;
import org.mbari.expd.jdbc.CtdDatumDAOImpl;

// You'll need a Dive object 
CtdDatumDAO dao = daoFactory.getCtdDatumDAO();
List<CtdDatum> ctd = dao.fetchCtdData(dive);
```

#### Camera logs

```java
// timecode, recordedTimestamp. Useful when you're doing dive quality analysis later on
import org.mbari.expd.CameraDatum;
import org.mbari.expd.CameraDatumDAO;
import org.mbari.expd.jdbc.CameraDatumDAOImpl;

// You'll need a Dive object 
CameraDatumDAO dao = daoFactory.getCameraDatumDAO();
List<CameraDatum> cam = dao.fetchCameraData(dive);
```