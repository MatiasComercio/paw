package ar.edu.itba.paw.webapp.models;
import java.util.List;


public class TranscriptSemesterDTO {
  List<TranscriptGradeDTO> semester;

  public TranscriptSemesterDTO() {
  }

  public TranscriptSemesterDTO(List<TranscriptGradeDTO> semester) {
    this.semester = semester;
  }

  public List<TranscriptGradeDTO> getSemester() {
    return semester;
  }

  public void setSemester(List<TranscriptGradeDTO> semester) {
    this.semester = semester;
  }
}
