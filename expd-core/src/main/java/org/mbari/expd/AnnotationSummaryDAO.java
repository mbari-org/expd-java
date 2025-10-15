package org.mbari.expd;

public interface AnnotationSummaryDAO {

  AnnotationSummary findByDive(Dive dive);
  AnnotationSummary findByDive(String platform, Integer diveNumber);

}