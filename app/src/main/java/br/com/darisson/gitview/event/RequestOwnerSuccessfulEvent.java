package br.com.darisson.gitview.event;

public class RequestOwnerSuccessfulEvent {
    private Integer id;

    public RequestOwnerSuccessfulEvent(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
