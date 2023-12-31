package ProjectsManagmentBackEnd.services;

import ProjectsManagmentBackEnd.dtos.DemandDTO;
import ProjectsManagmentBackEnd.dtos.ProjectMemberShipInvitationDTO;
import ProjectsManagmentBackEnd.entity.ProjectMemberShipInvitation.ProjectMemberShipInvitation;
import ProjectsManagmentBackEnd.entity.ProjectMemberShipInvitation.ProjectMemberShipInvitationState;
import ProjectsManagmentBackEnd.entity.project.Project;
import ProjectsManagmentBackEnd.entity.user.Role;
import ProjectsManagmentBackEnd.entity.user.RoleType;
import ProjectsManagmentBackEnd.entity.user.User;
import ProjectsManagmentBackEnd.exceptions.BusinessException;
import ProjectsManagmentBackEnd.mappers.ProjectMemberShipInvitationMapper;
import ProjectsManagmentBackEnd.repository.ProjectMemberShipInvitationRepository;
import ProjectsManagmentBackEnd.repository.ProjectRepository;
import ProjectsManagmentBackEnd.repository.RoleRepository;
import ProjectsManagmentBackEnd.repository.UserRepository;
import ProjectsManagmentBackEnd.security.JwtAuthenticationResponse;
import ProjectsManagmentBackEnd.utils.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectMemberShipInvitationImp {
    private ProjectMemberShipInvitationRepository projectMemberShipInvitationRepository;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private ProjectServiceImp projectServiceImp;
    private ProjectRoleServiceImp projectRoleServiceImp;
    private  UserServiceImp userServiceImp;
    private  EmailSenderServiceImp emailSenderServiceImp;
    public ResponseEntity getAllSentByProject(String projectId) throws BusinessException {
        User user= UserContext.currentUser();
        Project project=projectRepository.findById(projectId).get();
        Optional<Role> role=projectRoleServiceImp.getUserRoleForAProject(project,user);
        Role roleToSet;
        if(role.isPresent()){
            roleToSet=role.get();
        }else{

            return   ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<ProjectMemberShipInvitationDTO> projectMemberShipInvitationDTOList=
                projectMemberShipInvitationRepository
                        .findAllByFromAndProjectAndState(user,project,ProjectMemberShipInvitationState.NEW)
                        .stream()
                        .map(ProjectMemberShipInvitationMapper::convert).collect(Collectors.toList());

        JwtAuthenticationResponse newJwtAuthenticationResponse= userServiceImp.updateAuthoritiesForUser(roleToSet);
        Map response=new HashMap<>();
        response.put("invitations",projectMemberShipInvitationDTOList);
        response.put("jwtAuthenticationResponse",newJwtAuthenticationResponse);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public ResponseEntity<List<ProjectMemberShipInvitationDTO>> getAllReceived(){
        User user= UserContext.currentUser();
        List<ProjectMemberShipInvitationDTO> projectMemberShipInvitationDTOList=
                projectMemberShipInvitationRepository
                        .findAllByToAndState(user,ProjectMemberShipInvitationState.NEW)
                        .stream()
                        .map(ProjectMemberShipInvitationMapper::convert).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(projectMemberShipInvitationDTOList);
    }
    public ResponseEntity<ProjectMemberShipInvitationDTO> acceptInvitation(String InvitationId) throws BusinessException {
        User user= UserContext.currentUser();
        //to do add roles to the user
        Optional<ProjectMemberShipInvitation> projectMemberShipInvitation=
                projectMemberShipInvitationRepository.findByIdAndTo(InvitationId,user);
        if(projectMemberShipInvitation.isPresent()){
            ProjectMemberShipInvitation invitation=projectMemberShipInvitation.get();
            invitation.setState(ProjectMemberShipInvitationState.ACCEPTED);
            projectServiceImp.addMember(invitation.getProject().getId(), user.getId());
            projectMemberShipInvitationRepository.save(invitation);
            return ResponseEntity.status(HttpStatus.OK).body(ProjectMemberShipInvitationMapper.convert(invitation));

        }else{

            Map error=  new HashMap();
            error.put("error","projectMemberShipInvitation does not exist ");
            throw  new BusinessException("error",1111, error);
        }
    }
    public ResponseEntity<ProjectMemberShipInvitationDTO> declineInvitation(String InvitationId) throws BusinessException {
        User user= UserContext.currentUser();
        Optional<ProjectMemberShipInvitation> projectMemberShipInvitation=
                projectMemberShipInvitationRepository.findByIdAndTo(InvitationId,user);
        if(projectMemberShipInvitation.isPresent()){
            ProjectMemberShipInvitation invitation=projectMemberShipInvitation.get();
            invitation.setState(ProjectMemberShipInvitationState.REJECTED);
            projectMemberShipInvitationRepository.save(invitation);
            return ResponseEntity.status(HttpStatus.OK).body(ProjectMemberShipInvitationMapper.convert(projectMemberShipInvitation.get()));

        }else{

            Map error=  new HashMap();
            error.put("error","projectMemberShipInvitation does not exist ");
            throw  new BusinessException("error",1111, error);
        }


    }
    public ResponseEntity<ProjectMemberShipInvitationDTO> sendInvitation(String toUserId ,String projectId) throws BusinessException {
        User fromUser= UserContext.currentUser();
        User toUser= userRepository.findById(toUserId).get();
        Project project=projectRepository.findById(projectId).get();
        ProjectMemberShipInvitation invitation=new ProjectMemberShipInvitation();
        invitation.setTo(toUser);
        invitation.setFrom(fromUser);
        invitation.setProject(project);
        invitation.setState(ProjectMemberShipInvitationState.NEW);
        invitation.setCreationTime(new Date());
        invitation= projectMemberShipInvitationRepository.save(invitation);
        ProjectMemberShipInvitationDTO invitationDTO=ProjectMemberShipInvitationMapper.convert(invitation);
        //send email maybe
        emailSenderServiceImp.sendMail(toUser.getEmail(),toUser.getUsername(), fromUser.getUsername(),project.getShortName());


        return ResponseEntity.status(HttpStatus.OK).body(invitationDTO);
    }

}
