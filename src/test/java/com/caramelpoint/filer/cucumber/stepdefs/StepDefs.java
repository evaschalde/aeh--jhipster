package com.caramelpoint.filer.cucumber.stepdefs;

import com.caramelpoint.filer.FilerApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = FilerApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
