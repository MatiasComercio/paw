package ar.edu.itba.paw.webapp.models;

import java.util.LinkedList;
import java.util.List;

public class TranscriptDTO {

  private List<TranscriptSemesterDTO> transcript;

  public TranscriptDTO() {
  }

  public TranscriptDTO(List<List<TranscriptGradeDTO>> transcript) {
    this.transcript = new LinkedList<>();
    for(List<TranscriptGradeDTO> semester : transcript){
      this.transcript.add(new TranscriptSemesterDTO(semester));
    }
  }

  public List<TranscriptSemesterDTO> getTranscript() {
    return transcript;
  }

  public void setTranscript(List<TranscriptSemesterDTO> transcript) {
    this.transcript = transcript;
  }
}
