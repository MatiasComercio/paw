package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.webapp.models.AddressDTO;
import ar.edu.itba.paw.webapp.models.StudentIndexDTO;
import ar.edu.itba.paw.webapp.models.StudentShowDTO;
import ar.edu.itba.paw.webapp.models.StudentUpdateDTO;
import ar.edu.itba.paw.webapp.models.UserDTO;
import ar.edu.itba.paw.webapp.models.UserNewDTO;
import org.modelmapper.ModelMapper;

public class DTOEntityMapper {

  private final ModelMapper modelMapper;

  public DTOEntityMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  StudentIndexDTO convertToStudentIndexDTO(final Student student) {
    return modelMapper.map(student, StudentIndexDTO.class);
  }

  StudentShowDTO convertToStudentShowDTO(final Student student) {
    return modelMapper.map(student, StudentShowDTO.class);
  }

  Student convertToEntity(UserNewDTO userNewDTO){
    return modelMapper.map(userNewDTO, Student.class);
  }

  public AddressDTO convertToAddressDTO(Address address) {
    return modelMapper.map(address, AddressDTO.class);
  Student convertToStudent(final StudentUpdateDTO studentUpdateDTO) {
    return modelMapper.map(studentUpdateDTO, Student.class);
  }

  Student convertToUser(UserDTO userDTO){
    return modelMapper.map(userDTO, Student.class);
  }

}
