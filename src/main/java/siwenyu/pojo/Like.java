package siwenyu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    private Integer number;
    private Long userId;
    private Long videoId;
    private Long commentId;
}
