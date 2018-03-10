/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsLayer;

import VisualLayer.MainClass;

/**
 *
 * @author Yordanka
 */
public class ErrorMessages {

    public static final String INSERTION_ERROR = new LanguageSelector(MainClass.language).getProperty("tag_ins_error");
    public static final String ELIMINATION_ERROR = new LanguageSelector(MainClass.language).getProperty("tag_elim_error");
    public static final String MODIFICATION_ERROR = new LanguageSelector(MainClass.language).getProperty("tag_mod_error");
    public static final String EMPTY_FIELD_ERROR = new LanguageSelector(MainClass.language).getProperty("tag_empty_field_error");
    public static final String RECORD_EXIST_ERROR = new LanguageSelector(MainClass.language).getProperty("tag_record_exist_error");
    public static final String NO_SELECTION_ERROR = new LanguageSelector(MainClass.language).getProperty("tag_sel_error");
    public static final String LOGGED_USER_ERROR = new LanguageSelector(MainClass.language).getProperty("tag_user_logged_error");
}
