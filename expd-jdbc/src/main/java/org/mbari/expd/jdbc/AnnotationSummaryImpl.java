package org.mbari.expd.jdbc;

import java.util.Date;
import org.mbari.expd.AnnotationSummary;

/**
 * Implementation of the AnnotationSummary interface that represents
 * a summary of annotations related to a dive mission. This class
 * provides detailed information about the dive, including metadata
 * about the remotely operated vehicle (ROV), camera used, mission, and
 * the annotation process.
 *
 * This class includes properties such as the dive number, ROV name,
 * camera details, annotator details, timestamps, and other statistics
 * related to the annotations and video data. It also supports
 * getters and setters to read and manipulate these properties.
 */
public class AnnotationSummaryImpl implements AnnotationSummary {

  private Integer diveNumber;
  private String rovName;
  private String camera;
  private String notes;
  private String mission;
  private String application;
  private String annotators;
  private Date dateAnnotated;
  private String annotationFileName;
  private Boolean realTimeEdited;
  private String style;
  private Double hoursAnnotated;
  private Integer standardDefTapeCount;
  private Integer highDefTapeCount;
  private Integer videoSegmentCount;
  private Double hoursOfVideo;

  public AnnotationSummaryImpl(Integer diveNumber, String rovName, String camera, String notes,
                               String mission, String application, String annotators,
                               Date dateAnnotated, String annotationFileName,
                               Boolean realTimeEdited, String style, Double hoursAnnotated,
                               Integer standardDefTapeCount, Integer highDefTapeCount,
                               Integer videoSegmentCount, Double hoursOfVideo) {
    this.diveNumber = diveNumber;
    this.rovName = rovName;
    this.camera = camera;
    this.notes = notes;
    this.mission = mission;
    this.application = application;
    this.annotators = annotators;
    this.dateAnnotated = dateAnnotated;
    this.annotationFileName = annotationFileName;
    this.realTimeEdited = realTimeEdited;
    this.style = style;
    this.hoursAnnotated = hoursAnnotated;
    this.standardDefTapeCount = standardDefTapeCount;
    this.highDefTapeCount = highDefTapeCount;
    this.videoSegmentCount = videoSegmentCount;
    this.hoursOfVideo = hoursOfVideo;
  }

  /**
   * Returns the dive number associated with this annotation summary.
   * @return the dive number, or null if unknown
   */
  @Override
  public Integer getDiveNumber() {
    return diveNumber;
  }

  /**
   * Sets the dive number for this annotation summary.
   * @param diveNumber the dive number to set
   */
  public void setDiveNumber(Integer diveNumber) {
    this.diveNumber = diveNumber;
  }

  /**
   * Gets the full ROV (platform) name for the dive.
   * @return the ROV name
   */
  @Override
  public String getRovName() {
    return rovName;
  }

  /**
   * Sets the ROV (platform) name for the dive.
   * @param rovName the ROV name to set
   */
  public void setRovName(String rovName) {
    this.rovName = rovName;
  }

  /**
   * Gets the camera identifier or description used during the dive.
   * @return the camera information
   */
  @Override
  public String getCamera() {
    return camera;
  }

  /**
   * Sets the camera identifier or description used during the dive.
   * @param camera the camera information to set
   */
  public void setCamera(String camera) {
    this.camera = camera;
  }

  /**
   * Gets any free-form notes associated with the dive or annotations.
   * @return the notes text, may be null
   */
  @Override
  public String getNotes() {
    return notes;
  }

  /**
   * Sets free-form notes associated with the dive or annotations.
   * @param notes the notes text to set
   */
  public void setNotes(String notes) {
    this.notes = notes;
  }

  /**
   * Gets the mission name or identifier associated with the dive.
   * @return the mission name
   */
  @Override
  public String getMission() {
    return mission;
  }

  /**
   * Sets the mission name or identifier associated with the dive.
   * @param mission the mission name to set
   */
  public void setMission(String mission) {
    this.mission = mission;
  }

  /**
   * Gets the annotation application used to produce the summary (e.g., software name).
   * @return the application name
   */
  @Override
  public String getApplication() {
    return application;
  }

  /**
   * Sets the annotation application name used to produce the summary.
   * @param application the application name to set
   */
  public void setApplication(String application) {
    this.application = application;
  }

  /**
   * Gets the annotators who contributed to this dive's annotations.
   * @return a comma-separated list of annotator names, or null
   */
  @Override
  public String getAnnotators() {
    return annotators;
  }

  /**
   * Sets the annotators who contributed to this dive's annotations.
   * @param annotators a comma-separated list of annotator names
   */
  public void setAnnotators(String annotators) {
    this.annotators = annotators;
  }

  /**
   * Gets the date when the annotations were created or last edited.
   * @return the annotation date in UTC
   */
  @Override
  public Date getDateAnnotated() {
    return dateAnnotated;
  }

  /**
   * Sets the date when the annotations were created or last edited.
   * @param dateAnnotated the date to set (expected in UTC)
   */
  public void setDateAnnotated(Date dateAnnotated) {
    this.dateAnnotated = dateAnnotated;
  }

  /**
   * Gets the name of the annotation file associated with this dive, if any.
   * @return the annotation file name, or null if not applicable
   */
  @Override
  public String getAnnotationFileName() {
    return annotationFileName;
  }

  /**
   * Sets the name of the annotation file associated with this dive.
   * @param annotationFileName the file name to set
   */
  public void setAnnotationFileName(String annotationFileName) {
    this.annotationFileName = annotationFileName;
  }

  /**
   * Indicates whether the annotation file was edited in real time during the dive.
   * @return true if real-time edited; false otherwise; may be null if unknown
   */
  public Boolean isRealTimeEdited() {
    return realTimeEdited;
  }

  /**
   * Sets whether the annotation file was edited in real time during the dive.
   * @param realTimeEdited true if edited in real time; false otherwise
   */
  public void setRealTimeEdited(Boolean realTimeEdited) {
    this.realTimeEdited = realTimeEdited;
  }

  /**
   * Gets the annotation style or schema used (e.g., guidelines or format).
   * @return the style string
   */
  @Override
  public String getStyle() {
    return style;
  }

  /**
   * Sets the annotation style or schema used.
   * @param style the style string to set
   */
  public void setStyle(String style) {
    this.style = style;
  }

  /**
   * Gets the total number of hours that were actively annotated.
   * @return the number of annotated hours
   */
  @Override
  public Double getHoursAnnotated() {
    return hoursAnnotated;
  }

  /**
   * Sets the total number of hours that were actively annotated.
   * @param hoursAnnotated the annotated hours to set
   */
  public void setHoursAnnotated(Double hoursAnnotated) {
    this.hoursAnnotated = hoursAnnotated;
  }

  /**
   * Gets the number of standard-definition tapes recorded for this dive.
   * @return the count of SD tapes
   */
  @Override
  public Integer getStandardDefTapeCount() {
    return standardDefTapeCount;
  }

  /**
   * Sets the number of standard-definition tapes recorded for this dive.
   * @param standardDefTapeCount the SD tape count to set
   */
  public void setStandardDefTapeCount(Integer standardDefTapeCount) {
    this.standardDefTapeCount = standardDefTapeCount;
  }

  /**
   * Gets the number of high-definition tapes recorded for this dive.
   * @return the count of HD tapes
   */
  @Override
  public Integer getHighDefTapeCount() {
    return highDefTapeCount;
  }

  /**
   * Sets the number of high-definition tapes recorded for this dive.
   * @param highDefTapeCount the HD tape count to set
   */
  public void setHighDefTapeCount(Integer highDefTapeCount) {
    this.highDefTapeCount = highDefTapeCount;
  }

  /**
   * Gets the number of video file segments associated with this dive.
   * @return the number of video segments
   */
  @Override
  public Integer getVideoSegmentCount() {
    return videoSegmentCount;
  }

  /**
   * Sets the number of video file segments associated with this dive.
   * @param videoSegmentCount the number of video segments to set
   */
  public void setVideoSegmentCount(Integer videoSegmentCount) {
    this.videoSegmentCount = videoSegmentCount;
  }

  /**
   * Gets the total number of hours of video recorded for this dive.
   * @return the total hours of video
   */
  @Override
  public Double getHoursOfVideo() {
    return hoursOfVideo;
  }

  /**
   * Sets the total number of hours of video recorded for this dive.
   * @param hoursOfVideo the total hours of video to set
   */
  public void setHoursOfVideo(Double hoursOfVideo) {
    this.hoursOfVideo = hoursOfVideo;
  }
}