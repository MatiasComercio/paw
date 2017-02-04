package ar.edu.itba.paw.webapp.models;

import java.util.LinkedList;
import java.util.List;

public class TranscriptDTO {

  private List<TranscriptSemesterDTO> transcript;
  int totalCredits;
  int currentCredits;


  public TranscriptDTO() {
  }

  public TranscriptDTO(List<List<TranscriptGradeDTO>> transcript, int totalCredits, int currentCredits) {
    this.transcript = new LinkedList<>();
    for(List<TranscriptGradeDTO> semester : transcript){
      this.transcript.add(new TranscriptSemesterDTO(semester));
    }

    this.totalCredits = totalCredits;
    this.currentCredits = currentCredits;
  }

  public List<TranscriptSemesterDTO> getTranscript() {
    return transcript;
  }

  public void setTranscript(List<TranscriptSemesterDTO> transcript) {
    this.transcript = transcript;
  }

  public int getTotalCredits() {
    return totalCredits;
  }

  public void setTotalCredits(int totalCredits) {
    this.totalCredits = totalCredits;
  }

  public int getCurrentCredits() {
    return currentCredits;
  }

  public void setCurrentCredits(int currentCredits) {
    this.currentCredits = currentCredits;
  }
}
