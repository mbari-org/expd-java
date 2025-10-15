package org.mbari.expd.jdbc;

import java.util.Date;
import org.mbari.expd.AnnotationSummary;

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

  @Override
  public Integer getDiveNumber() {
    return diveNumber;
  }

  public void setDiveNumber(Integer diveNumber) {
    this.diveNumber = diveNumber;
  }

  @Override
  public String getRovName() {
    return rovName;
  }

  public void setRovName(String rovName) {
    this.rovName = rovName;
  }

  @Override
  public String getCamera() {
    return camera;
  }

  public void setCamera(String camera) {
    this.camera = camera;
  }

  @Override
  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  @Override
  public String getMission() {
    return mission;
  }

  public void setMission(String mission) {
    this.mission = mission;
  }

  @Override
  public String getApplication() {
    return application;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  @Override
  public String getAnnotators() {
    return annotators;
  }

  public void setAnnotators(String annotators) {
    this.annotators = annotators;
  }

  @Override
  public Date getDateAnnotated() {
    return dateAnnotated;
  }

  public void setDateAnnotated(Date dateAnnotated) {
    this.dateAnnotated = dateAnnotated;
  }

  @Override
  public String getAnnotationFileName() {
    return annotationFileName;
  }

  public void setAnnotationFileName(String annotationFileName) {
    this.annotationFileName = annotationFileName;
  }

  public Boolean isRealTimeEdited() {
    return realTimeEdited;
  }

  public void setRealTimeEdited(Boolean realTimeEdited) {
    this.realTimeEdited = realTimeEdited;
  }

  @Override
  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  @Override
  public Double getHoursAnnotated() {
    return hoursAnnotated;
  }

  public void setHoursAnnotated(Double hoursAnnotated) {
    this.hoursAnnotated = hoursAnnotated;
  }

  @Override
  public Integer getStandardDefTapeCount() {
    return standardDefTapeCount;
  }

  public void setStandardDefTapeCount(Integer standardDefTapeCount) {
    this.standardDefTapeCount = standardDefTapeCount;
  }

  @Override
  public Integer getHighDefTapeCount() {
    return highDefTapeCount;
  }

  public void setHighDefTapeCount(Integer highDefTapeCount) {
    this.highDefTapeCount = highDefTapeCount;
  }

  @Override
  public Integer getVideoSegmentCount() {
    return videoSegmentCount;
  }

  public void setVideoSegmentCount(Integer videoSegmentCount) {
    this.videoSegmentCount = videoSegmentCount;
  }

  @Override
  public Double getHoursOfVideo() {
    return hoursOfVideo;
  }

  public void setHoursOfVideo(Double hoursOfVideo) {
    this.hoursOfVideo = hoursOfVideo;
  }
}