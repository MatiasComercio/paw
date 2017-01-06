package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.webapp.models.StudentDTO;
import org.modelmapper.ModelMapper;

public class DTOEntityMapper {

  private final ModelMapper modelMapper;

  public DTOEntityMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public StudentDTO convertToDTO(Student student){
    StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
    return studentDTO;
  }

  public Student convertToEntity(StudentDTO studentDTO){
    Student student = modelMapper.map(studentDTO, Student.class);
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
