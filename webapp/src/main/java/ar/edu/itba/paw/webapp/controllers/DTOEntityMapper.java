package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.webapp.models.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;
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
    finalInscription.setHistory(finalStudents);
    FinalInscriptionDTO finalInscriptionDTO = modelMapper.map(finalInscription, FinalInscriptionDTO.class);
    return finalInscriptionDTO;
  }

  FinalInscriptionIndexDTO convertToFinalInscriptionIndexDTO(FinalInscription finalInscription) {
    return modelMapper.map(finalInscription, FinalInscriptionIndexDTO.class);
  }

  FinalInscription convertToFinalInscription(FinalInscriptionDTO finalinscriptionDTO, Course course) {
    FinalInscription finalinscription = modelMapper.map(finalinscriptionDTO, FinalInscription.class);
    finalinscription.setState(FinalInscription.FinalInscriptionState.OPEN);
    finalinscription.setId(null);
    finalinscription.setCourse(course);
    return finalinscription;
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

  UserSessionDTO convertToAdminSessionDTO(final User user, Collection<? extends GrantedAuthority> authorities) {
    final UserSessionDTO adminSessionDTO = new UserSessionDTO();
    final AddressDTO addressDTO = convertToAddressDTO(user.getAddress());

    adminSessionDTO.setDni(user.getDni());
    adminSessionDTO.setFirstName(user.getFirstName());
    adminSessionDTO.setLastName(user.getLastName());
    adminSessionDTO.setGenre(user.getGenre());
    adminSessionDTO.setBirthday(user.getBirthday());
    adminSessionDTO.setEmail(user.getEmail());
    adminSessionDTO.setRole(user.getRole());
    adminSessionDTO.setAddress(addressDTO);
    adminSessionDTO.setAuthorities(authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

    return adminSessionDTO;
  }

  StudentSessionDTO convertToStudentSessionDTO(final Student student, final Collection<? extends GrantedAuthority> authorities) {
    final StudentSessionDTO studentSessionDTO = new StudentSessionDTO();
    final AddressDTO addressDTO = convertToAddressDTO(student.getAddress());

    studentSessionDTO.setDni(student.getDni());
    studentSessionDTO.setFirstName(student.getFirstName());
    studentSessionDTO.setLastName(student.getLastName());
    studentSessionDTO.setGenre(student.getGenre());
    studentSessionDTO.setBirthday(student.getBirthday());
    studentSessionDTO.setEmail(student.getEmail());
    studentSessionDTO.setRole(student.getRole());
    studentSessionDTO.setDocket(student.getDocket());
    studentSessionDTO.setAddress(addressDTO);
    studentSessionDTO.setAuthorities(authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

    return studentSessionDTO;
  }

  List<StudentWithGradeDTO> convertToStudentsWithGradeDTO(final Map<Student, Grade> studentsWithGrade) {
    final List<StudentWithGradeDTO> studentWithGradeDTOS = new ArrayList<>(studentsWithGrade.size());

    for(final Map.Entry<Student, Grade> entry : studentsWithGrade.entrySet()) {
      final StudentIndexDTO studentIndexDTO = convertToStudentIndexDTO(entry.getKey());
      final Grade grade = entry.getValue();
      final StudentWithGradeDTO studentWithGradeDTO = new StudentWithGradeDTO();

      studentWithGradeDTO.setStudent(studentIndexDTO);
      studentWithGradeDTO.setGrade(grade.getGrade());

      studentWithGradeDTOS.add(studentWithGradeDTO);
    }

    return studentWithGradeDTOS;
  }
}
