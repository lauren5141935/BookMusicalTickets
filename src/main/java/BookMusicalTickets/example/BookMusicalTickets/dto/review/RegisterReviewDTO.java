package BookMusicalTickets.example.BookMusicalTickets.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RegisterReviewDTO {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Request {

    @NotNull
    @Size(max = 100, message = "리뷰 내용은 최대 100자까지 입력 가능합니다.")
    private String content;

    private String photo_url;

    @NotNull
    @Min(value = 1, message = "평점은 1점 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 5점 이하이어야 합니다.")
    private Integer rating;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Response {

    private Long id;
    private String nickname;
    private String musicalTitle;
    private String content;
    private String photo_url;
    private Integer rating;

  }
}
