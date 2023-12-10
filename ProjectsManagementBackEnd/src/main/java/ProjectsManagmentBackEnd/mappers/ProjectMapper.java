package ProjectsManagmentBackEnd.mappers;

import ProjectsManagmentBackEnd.dtos.project.ProjectDTO;
import ProjectsManagmentBackEnd.dtos.project.ProjectShortDTO;
import ProjectsManagmentBackEnd.entity.project.Project;

import java.util.stream.Collectors;

public class ProjectMapper {
    public static ProjectDTO convert(Project in) {

        if (in != null) {
            final ProjectDTO out = new ProjectDTO();
            out.setId(in.getId());
            out.setPublic(in.isPublic());
            out.setActive(in.isActive());
            out.setDescription(in.getDescription());
            out.setTheme(in.getTheme());
            out.setType(in.getType());
            out.setLongName(in.getLongName());
            out.setShortName(in.getShortName());
            out.setLastUpdate(in.getLastUpdate());
           out.setProjectGroups(in.getProjectGroups().stream().map(ProjectGroupMapper::convert).collect(Collectors.toList()));
          //  out.setEvents(in.getEvents());



            return out;
        }
        return null;
    }
    public static Project  convert(ProjectDTO in) {

        if (in != null) {
            final Project out = new Project();
            out.setId(in.getId());
            out.setPublic(in.isPublic());
            out.setActive(in.isActive());
            out.setDescription(in.getDescription());
            out.setTheme(in.getTheme());
            out.setType(in.getType());
            out.setLongName(in.getLongName());
            out.setShortName(in.getShortName());
            out.setLastUpdate(in.getLastUpdate());
           // out.setEvents(in.getEvents());
            out.setProjectGroups(in.getProjectGroups().stream().map(ProjectGroupMapper::convert).collect(Collectors.toList()));
            return out;
        }
        return null;
    }
    public static ProjectShortDTO convertShort(Project in) {

        if (in != null) {
            final ProjectShortDTO out = new ProjectShortDTO();
            out.setId(in.getId());
            out.setLongName(in.getLongName());
            out.setShortName(in.getShortName());
            return out;
        }
        return null;
    }
}
