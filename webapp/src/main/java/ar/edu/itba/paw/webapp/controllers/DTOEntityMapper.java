package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.webapp.models.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;

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

  AddressDTO convertToAddressDTO(Address address) {
    return modelMapper.map(address, AddressDTO.class);
  }

  Student convertToStudent(final StudentsUpdateDTO studentUpdateDTO) {
    return modelMapper.map(studentUpdateDTO, Student.class);
  }

  Student convertToStudent(final UserNewDTO userDTO){
    return modelMapper.map(userDTO, Student.class);
  }

  Address convertToAddress(AddressDTO addressDTO) {
    return modelMapper.map(addressDTO, Address.class);
  }

  CourseDTO convertToCourseDTO(Course course) {
    return modelMapper.map(course, CourseDTO.class);
  }

  Course convertToCourse(final CourseDTO courseDTO) {
    final MatchingStrategy originalMatchingStrategy = modelMapper.getConfiguration().getMatchingStrategy();

    //TODO: Check if this can be improved
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    final Course course = modelMapper.map(courseDTO, Course.class);
    modelMapper.getConfiguration().setMatchingStrategy(originalMatchingStrategy);

    return course;
  }
}
