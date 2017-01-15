package ar.edu.itba.paw.webapp.models;

import java.util.List;

public class TranscriptSemesterDTO {
  List<TranscriptGradeDTO> grades;

  public TranscriptSemesterDTO() {
  }

  public TranscriptSemesterDTO(List<TranscriptGradeDTO> grades) {
    this.grades = grades;
  }

  public List<TranscriptGradeDTO> getGrades() {
    return grades;
  }

  public void setGrades(List<TranscriptGradeDTO> grades) {
    this.grades = grades;
  }
}
