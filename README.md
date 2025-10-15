# EXPD

EXPD is a Java API for simplifying access to MBARI's expedition database (also called EXPD). It uses JDBC for data access. It also contains conditional logic to allow tasks such as selecting the best available navigation data and oxygen data.

### To get to the expedition (EXPD) data, you need to add the following repository to your helidon project's pom.xml file:

```xml
<repositories>
  <repository>
    <snapshots>
      <enabled>false</enabled>
    </snapshots>
    <id>bintray-org-mbari-maven</id>
    <name>bintray</name>
    <url>https://dl.bintray.com/org-mbari/maven</url>
  </repository>
</repositories>
```

Then add the following dependency:

```
<dependency>
	<groupId>org.mbari</groupId>
	<artifactId>expd-jdbc</artifactId>
	<version>1.5.3.jre11</version>
	<type>pom</type>
</dependency>
```

## Java pseudo-code examples (expect typos):

### To get a list of all ROVs

```java
import org.mbari.expd.jdbc.BaseDAOImpl;

List<String> rovs = BaseDAOImpl.getAllRovNames();
```

### To get all dives for a particular ROV:

```java
import org.mbari.expd.DiveDAO;
import org.mbari.expd.jdbc.DiveDAOImpl;

DiveDAO dao = new DiveDAOImpl();

Collection<Dive> divesForRov = dao.findByPlatform(rovNameAsString);


// Example converting them to a list of dive numbers:
List<Integer> diveNumbersForRov = divesForRov.stream()
        .map(Dive::getDiveNumber)
        .sorted()
        .collect(Collectors.toList());
```

### To get a dive object by ROV name and dive number

```java
import org.mbari.expd.DiveDAO;
import org.mbari.expd.jdbc.DiveDAOImpl;

DiveDAO dao = new DiveDAOImpl();

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
NavigationDAO dao = new NavigationDAOImpl();
List<NavigationDatum> nav = dao.fetchBestnavigationData(dive);
```

#### CTD

```java
// salinity (psu), temperature (celsius), pressure (dbar)
import org.mbari.expd.CtdDatum;
import org.mbari.expd.CtdDatumDAO;
import org.mbari.expd.jdbc.CtdDatumDAOImpl;

// You'll need a Dive object 
CtdDatumDAO dao = new CtdDatumDAOImpl();
List<CtdDatum> ctd = dao.fetchCtdData(dive)
```

#### Camera logs

```java
// timecode, recordedTimestamp. Useful when you're doing dive quality analysis later on
import org.mbari.expd.CameraDatum;
import org.mbari.expd.CameraDatumDAO;
import org.mbari.expd.jdbc.CameraDatumDAOImpl;

// You'll need a Dive object 
CameraDatumDAO dao = new CameraDatumDAOImpl();
List<CameraDatum> cam = dao.fetchCameraData(dive);
```