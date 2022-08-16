package ch.evolutionsoft.game.tictactoe.view;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("localeBean")
@SessionScoped
public class LocaleBean implements Serializable {

    private Locale locale = Locale.ENGLISH;

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        locale = Locale.forLanguageTag(language);
    }

}
