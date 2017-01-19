package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.TranscriptGrade;
import ar.edu.itba.paw.models.FinalInscription;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.webapp.models.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;

import java.util.Set;
import java.util.stream.Collectors;

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

  TranscriptGradeDTO convertToTranscriptGradeDTO(TranscriptGrade transcriptGrade) {
    return modelMapper.map(transcriptGrade, TranscriptGradeDTO.class);
  }

  Grade convertToGrade(GradeDTO gradeDTO, Student student, Course course, Integer id) {
    Grade grade = new Grade.Builder(id, student, course.getId(), course.getCourseId(), gradeDTO.getGrade())
            .modified(gradeDTO.getModified()).build();
    grade.setCourse(course);
    return grade;
  }

  Course convertToCourse(final CourseDTO courseDTO) {
    final MatchingStrategy originalMatchingStrategy = modelMapper.getConfiguration().getMatchingStrategy();

    //TODO: Check if this can be improved
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    final Course course = modelMapper.map(courseDTO, Course.class);
    modelMapper.getConfiguration().setMatchingStrategy(originalMatchingStrategy);

    return course;
  }

  FinalInscriptionDTO convertToFinalInscriptionDTO(FinalInscription finalInscription, Set<Student> finalStudents) {
    final FinalInscriptionDTO finalInscriptionDTO = new FinalInscriptionDTO();
    final CourseDTO courseDTO = convertToCourseDTO(finalInscription.getCourse());

    finalInscriptionDTO.setCourse(courseDTO);
    finalInscriptionDTO.setId(finalInscription.getId());
    finalInscriptionDTO.setFinalExamDate(finalInscription.getFinalExamDate());
    finalInscriptionDTO.setVacancy(finalInscription.getVacancy());
    finalInscriptionDTO.setMaxVacancy(finalInscription.getMaxVacancy());
    finalInscriptionDTO.setState(finalInscription.getState());

    //TODO: The students can't be obtained from the finalInscription because Hibernate throws a Lazy initialization
    finalInscriptionDTO.setStudents(finalStudents.stream().map(this::convertToStudentIndexDTO).collect(Collectors.toList()));

    return finalInscriptionDTO;
  }

  public FinalInscriptionIndexDTO convertToFinalInscriptionIndexDTO(FinalInscription finalInscription) {
    return modelMapper.map(finalInscription, FinalInscriptionIndexDTO.class);
  }

  AdminDTO converToAdminDTO(final Admin admin) {
    return modelMapper.map(admin, AdminDTO.class);
  }

  Admin convertToAdmin(AdminNewDTO adminNewDTO) {
    return modelMapper.map(adminNewDTO, Admin.class);
  }

  AdminShowDTO converToAdminShowDTO(final Admin admin) {
    return modelMapper.map(admin, AdminShowDTO.class);
  }

  Admin convertToAdmin(final AdminsUpdateDTO adminsUpdateDTO) {
    return modelMapper.map(adminsUpdateDTO, Admin.class);
  }
}
