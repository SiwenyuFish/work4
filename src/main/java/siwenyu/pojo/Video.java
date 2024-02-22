package siwenyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private Long id;
    private Long userId;
    private String videoUrl;
    private String coverUrl;
    private String title;
    private String description;
    private Integer visitCount;
    private Integer commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
