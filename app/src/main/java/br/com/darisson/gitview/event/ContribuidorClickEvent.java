package br.com.darisson.gitview.event;

public class ContribuidorClickEvent {

    private String login;

    public ContribuidorClickEvent(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
