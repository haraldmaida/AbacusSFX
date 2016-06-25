# Abacus SFX &nbsp;&nbsp;&nbsp; [![Bintray JCenter](https://img.shields.io/bintray/v/haraldmaida/maven/abacussfx.svg?label=release&style=flat-square)](https://bintray.com/haraldmaida/maven/abacussfx) [![License](http://img.shields.io/:license-Apache%202.0-red.svg?style=flat-square)](http://www.apache.org/licenses/LICENSE-2.0.txt)

A software [Abacus](https://en.wikipedia.org/wiki/Abacus) implemented in Scala as [ScalaFX](http://www.scalafx.org) application.

This project started mainly to demonstrate the usage [ScalaTestFX](https://github.com/haraldmaida/ScalaTestFX) for automated UI-tests and to evaluate improvements and enhancements for [ScalaTestFX](https://github.com/haraldmaida/ScalaTestFX). Meanwhile this Abacus application grew to a quite feature rich implementation. Here are some of the enhanced features:

- switch between different types of Abaci: Soroban (Japanese), Suanban (Chinese) and Schoty (Russian)
- display the current number of each rod in a split-flap display

[![Build Status](https://img.shields.io/travis/haraldmaida/AbacusSFX/master.svg?style=flat-square)](https://travis-ci.org/haraldmaida/AbacusSFX)

## Status

A first version of the Abacus application is finished and works quite well.

Unit tests for the domain layer are available.

Now it's time to write the ScalaTestFX test cases to test the UI ;-)

## Goals

The two goals of this project are:

1. creating a small application to demonstrate the usage of [ScalaTestFX](https://github.com/haraldmaida/ScalaTestFX) with.

2. playing around with different styles of writing test code to find most efficient ways of writing and maintaining test cases for automated UI-tests.

As a result I hope to create ideas for improving [ScalaTestFX](https://github.com/haraldmaida/ScalaTestFX), especially the test DSL for controlling and assertion of the UI under test.

## Motivation

I wanted to create a small [ScalaFX](http://www.scalafx.org) application to demonstrate the usage of  [ScalaTestFX](https://github.com/haraldmaida/ScalaTestFX) based on a "real world" application. While i was groping around for ideas what this application should be I stumbled over the [Abacus JavaFX application created by Dierk](https://github.com/Dierk/learnJavaFx) as a tutorial application for learning JavaFX.

Inspired by Dierk's Abacus application I got fascinated in Abaci in general.



## License

    Copyright (C) 2016 Innoave.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
  
