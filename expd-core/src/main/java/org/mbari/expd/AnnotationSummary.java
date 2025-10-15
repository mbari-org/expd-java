package org.mbari.expd;

import java.util.Date;

public interface AnnotationSummary {

  Integer getDiveNumber();
  String getRovName();
  String getCamera();
  String getNotes();
  String getMission();
  String getApplication();
  String getAnnotators();
  Date getDateAnnotated();
  String getAnnotationFileName();
  Boolean isRealTimeEdited();
  String getStyle();
  Double getHoursAnnotated();
  Integer getStandardDefTapeCount();
  Integer getHighDefTapeCount();
  Integer getVideoSegmentCount();
  Double getHoursOfVideo();

}