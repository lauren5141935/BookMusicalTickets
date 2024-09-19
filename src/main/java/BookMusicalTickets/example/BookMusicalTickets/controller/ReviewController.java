package BookMusicalTickets.example.BookMusicalTickets.controller;

import BookMusicalTickets.example.BookMusicalTickets.dto.review.RegisterReviewDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.review.ReviewDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.review.UpdateReviewDTO;
import BookMusicalTickets.example.BookMusicalTickets.service.ReviewService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping
  public RegisterReviewDTO.Response registerReview(
      @RequestParam("userId") Long customerId,
      @RequestParam("ticketId") Long ticketId,
      @RequestBody RegisterReviewDTO.Request request
  ) {
    return RegisterReviewDTO.Response.from(
        this.reviewService.registerReview(customerId, ticketId, request));
  }

  @PatchMapping("/{reviewId}")
  public UpdateReviewDTO.Response updateReview(
      @RequestParam("customerId") Long customerId,
      @PathVariable("reviewId") Long reviewId,
      @RequestBody UpdateReviewDTO.Request request
  ) {
    return UpdateReviewDTO.Response.from(
        this.reviewService.updateReview(customerId, reviewId, request));
  }

  @GetMapping("/users/{userId}")
  public Page<ReviewDTO> getAllReview(
      @PathVariable("userId") Long customerId,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "5") int size
  ) {
    return reviewService.getAllReview(customerId, page, size);
  }

  @DeleteMapping("/{reviewId}")
  public ResponseEntity<?> deleteReview(
      @RequestParam("userId") Long customerId,
      @PathVariable("reviewId") Long reviewId
  ) {
    this.reviewService.deleteReview(customerId, reviewId);
    return ResponseEntity.ok("해당 리뷰를 삭제하였습니다.");
  }
}