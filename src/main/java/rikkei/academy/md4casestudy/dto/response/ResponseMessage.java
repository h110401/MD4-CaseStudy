package rikkei.academy.md4casestudy.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class ResponseMessage {
    private List<String> message;

    public ResponseMessage(List<String> messages) {
        this.message = new ArrayList<>(messages);
    }

    public ResponseMessage(String message) {
        this.message = Collections.singletonList(message);
    }
}
