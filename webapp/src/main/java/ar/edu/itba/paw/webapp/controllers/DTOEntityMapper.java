package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.webapp.models.StudentIndexDTO;
import ar.edu.itba.paw.webapp.models.UserDTO;
import org.modelmapper.ModelMapper;

public class DTOEntityMapper {

  private final ModelMapper modelMapper;

  public DTOEntityMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  /* default */ StudentIndexDTO convertToDTO(Student student){
    return modelMapper.map(student, StudentIndexDTO.class);
  }

  /* default */ Student convertToEntity(UserDTO userDTO){
    return modelMapper.map(userDTO, Student.class);
  }

}
