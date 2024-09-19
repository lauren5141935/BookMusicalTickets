package BookMusicalTickets.example.BookMusicalTickets.mapper;

import BookMusicalTickets.example.BookMusicalTickets.domain.user.Admin;
import BookMusicalTickets.example.BookMusicalTickets.dto.user.AdminDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.user.AdminDTOPassword;

public class AdminMapper {

  private AdminMapper() {}

  public static Admin adminDTOToAdmin(AdminDTO adminDTO) {
    return Admin.builder()
        .loginId(adminDTO.getLoginId())
        .name(adminDTO.getName())
        .password(adminDTO.getPassword())
        .build();
  }

  public static AdminDTO adminToAdminDTO(Admin admin) {
    return AdminDTO.builder()
        .loginId(admin.getLoginId())
        .name(admin.getName())
        .build();
  }

  public static AdminDTOPassword adminToAdminDTOPassword(Admin admin) {
    return AdminDTOPassword.builder()
        .adminId(admin.getAdminId())
        .loginId(admin.getLoginId())
        .name(admin.getName())
        .build();
  }
}