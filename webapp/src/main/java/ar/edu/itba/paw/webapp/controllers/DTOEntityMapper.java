package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.webapp.models.StudentIndexDTO;
import org.modelmapper.ModelMapper;

public class DTOEntityMapper {

  private final ModelMapper modelMapper;

  public DTOEntityMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  /* default */ StudentIndexDTO convertToDTO(Student student){
    return modelMapper.map(student, StudentIndexDTO.class);
  }

  /* default */ Student convertToEntity(StudentIndexDTO studentIndexDTO){
    Student student = modelMapper.map(studentIndexDTO, Student.class);
//    post.setSubmissionDate(postDto.getSubmissionDateConverted(
//            userService.getCurrentUser().getPreference().getTimezone()));
//    if (postDto.getId() != null) {
//      Post oldPost = postService.getPostById(postDto.getId());
//      post.setRedditID(oldPost.getRedditID());
//      post.setSent(oldPost.isSent());
//    }
    return student;
  }

}
