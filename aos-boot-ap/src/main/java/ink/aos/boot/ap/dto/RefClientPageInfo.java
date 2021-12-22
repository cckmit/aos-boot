package ink.aos.boot.ap.dto;

import lombok.Data;

@Data
public class RefClientPageInfo {
    
    private int pageSize;
    private int currPageIndex;
    private int pageCount;

    public RefClientPageInfo() {
        this.pageSize = 100;

        this.currPageIndex = 0;
    }
}