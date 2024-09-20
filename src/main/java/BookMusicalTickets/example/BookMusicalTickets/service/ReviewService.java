package BookMusicalTickets.example.BookMusicalTickets.service;

import BookMusicalTickets.example.BookMusicalTickets.dto.review.RegisterReviewDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.review.ReviewDTO;
import BookMusicalTickets.example.BookMusicalTickets.dto.review.UpdateReviewDTO;
import org.springframework.data.domain.Page;

public interface ReviewService {

  ReviewDTO registerReview(Long customerId, Long ticketId, RegisterReviewDTO.Request request);

  ReviewDTO updateReview(Long customerId, Long reviewId, UpdateReviewDTO.Request request);

  Page<ReviewDTO> getAllReview(Long customerId, int page, int size);

  void deleteReview(Long customerId, Long reviewId);
}
