package com.luizalabs.infrastructure.di;

import com.google.inject.servlet.GuiceFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class GuiceWebFilter extends GuiceFilter {
}