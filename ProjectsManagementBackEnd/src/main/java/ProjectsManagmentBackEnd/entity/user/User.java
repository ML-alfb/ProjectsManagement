package ProjectsManagmentBackEnd.entity.user;

import ProjectsManagmentBackEnd.entity.ProjectMemberShipInvitation.ProjectMemberShipInvitation;
import ProjectsManagmentBackEnd.entity.chat.ChatContact;
import ProjectsManagmentBackEnd.entity.demand.Demand;
import ProjectsManagmentBackEnd.entity.event.Event;
import ProjectsManagmentBackEnd.entity.event.UserEvent;
import ProjectsManagmentBackEnd.entity.project.ProjectGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String username;

    private  String password;

    @Column(unique = true)
    private  String email;


    private Boolean enabled;

   @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
   @JoinTable(
           name = "user_project_group",
           joinColumns = @JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name = "group_id")
   )
   private Set<ProjectGroup> userGroups;

    @ManyToOne
    private Role role;
    @OneToMany
    private Set<UserEvent> events;

    @OneToMany
    private List<ProjectMemberShipInvitation> invitations;

    @OneToMany
    private List<Demand>demands;
    @OneToMany
    private List<ChatContact>contacts;

    @Temporal(TemporalType.TIMESTAMP)
    private  java.sql.Timestamp lastPasswordResetDate;

}
