package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.webapp.models.StudentIndexDTO;
import ar.edu.itba.paw.webapp.models.StudentShowDTO;
import ar.edu.itba.paw.webapp.models.UserDTO;
import org.modelmapper.ModelMapper;

public class DTOEntityMapper {

  private final ModelMapper modelMapper;

  public DTOEntityMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public StudentIndexDTO convertToStudentIndexDTO(final Student student) {
    return modelMapper.map(student, StudentIndexDTO.class);
  }

  public StudentShowDTO convertToStudentShowDTO(final Student student) {
    return modelMapper.map(student, StudentShowDTO.class);
  }

  Student convertToEntity(UserDTO userDTO){
    return modelMapper.map(userDTO, Student.class);
  }

}
