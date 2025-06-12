// src/main/java/com/example/moviereviewmvp/dto/ReviewDto.java
package com.example.moviereviewmvp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
// import lombok.Getter; // Nếu dùng Lombok
// import lombok.Setter;

// @Getter // Lombok
// @Setter // Lombok
public class ReviewDto {

    // Không cần movieId ở đây nếu chúng ta truyền movieId qua PathVariable trong controller
    // Tuy nhiên, nếu form phức tạp hơn hoặc bạn muốn truyền movieId trong body, bạn có thể thêm vào.

    @NotNull(message = "Điểm đánh giá không được để trống.")
    @Min(value = 1, message = "Điểm đánh giá phải ít nhất là 1 sao.")
    @Max(value = 5, message = "Điểm đánh giá không được vượt quá 5 sao.")
    private Integer ratingValue;

    @NotEmpty(message = "Nội dung bình luận không được để trống.")
    private String commentText;

    // Constructors (không bắt buộc nếu dùng cho form binding và có getter/setter)
    public ReviewDto() {
    }

    public ReviewDto(Integer ratingValue, String commentText) {
        this.ratingValue = ratingValue;
        this.commentText = commentText;
    }

    // Getters and Setters (Rất quan trọng cho việc binding dữ liệu từ form và validation)
    public Integer getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Integer ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}