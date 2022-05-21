# MoodGuru

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
It is an app to track users' daily mood (i.e a journal but to track how they are feeling every day). The app will display a variety of emotions to the users as a multiple choice question and they can choose whichever emotion that describes them the best of that day. Users will also be able to input ratings of their overall mood and specific emotions on a scale of 1-10. 

After a few days of inputting the users' mood, the app can display a chart of how the users have been feeling so far and give a rating of whether they have been feeling happy or not. It can give the users some suggestions on how to improve/ maintain their mental health based on that rating. Users can also input their own experience/suggestions into the pool so that others can see their suggestions.

Additionally, there will also be a note-taking/ journaling feature where the users can write down what they have been doing, how they have been feeling and their reflection for the day.

### Progress
#### Milestone 2 (5/15/2022 - 5/21/2022)
![MS2-1](https://media.giphy.com/media/GO7QE8FCtTTGqkW88C/giphy.gif)
![MS2-2](https://media.giphy.com/media/vmznPTYn6inuaMpcH1/giphy.gif)


#### Milestone 1 (5/7/2022 - 5/14/2022)
![MS1](https://media.giphy.com/media/Xi9TjrhRnRzpuqI1yn/giphy.gif)

### App Evaluation

- **Category:** Emotion, health, informational
- **Mobile:** Instead of a desktop website, it is much more convenient and practical for the users to use their phone to track their mood and journal their daily lives on a mobile app. Whenever the users want to reflect their emotions, they can just do so in a blink of an eye in their own mobile device.
- **Story:** There have been other similar mood tracker Android apps in the market already. (**TODO: Look more into features in Android mood tracker apps!!**)
- **Market:** This app focuses on younger people such as college students and young working adults. But anyone could find this app helpful.
- **Habit:** Since this app's main purpose is to reflect and journal, it could form a healthy habit for the users. If they find suggestions or motivation notes helpful, they would be more likely to use it and gradually form a habit. 
- **Scope:** There is a lot of potential to increase the scope of this app since there are many extra features that can be implemented besides inputting daily moods and journaling. However, as of now, the required user stories should be helpful enough to the users.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

As a user, I want to keep track of my mental health using these features:
- [x] Select adjective(s) that represents their feelings for certain moments of their days.
- [x] Rate their emotions on a scale of 1-5
- [ ] Look at a chart to visualize the changes of their emotion over time.
- [x] Write events, comments or self-motivation specific to that emotion. 
- [x] Receive suggestions on how to motivate themselves



**Optional Nice-to-have Stories**

- [x] Users can login.
- [ ] Users can link Spotify to their app so that they can listen to music while reflecting their mood.
- [ ] Users can make their journals and adjective public to the community. 
- [ ] Users can send suggestions directly to other users.
- [ ] Users can set up filters to avoid receiving messages containing certain words.
- [ ] Users can customize the adjectives to describe their specific emotion.
- [ ] Users can customize the emojis of the adjectives.
- [ ] Users can set up a reminder so that the alarm will go off at a fixed time to remind users to use the app and reflect on their day.
- [x] Users can write their journal first and let the emotion be detected by the app. 
- [ ] Users can be provided with customized suggestions based on the topic of their journal. 
- [x] User can have number associate to each emotion so that they can see their average emotion. 
- [ ] User can change the horizontal axis/time of chart


### 2. Screen Archetypes

* Journal
   * Allows users to look at the summary of their past journals (i.e first few lines each post) as well as suggestions.
   * Bottom navigation menu with the "Compose new journal" feature
* Compose
   * Allows users to select adjective(s) that represents their feelings for certain moments of their days.
   * Users can rate their emotions on a scale of 1-5
   * Users can write events, comments or self-motivation specific to that emotion.
* Suggestion
    * Users will receive suggestions/ quotes on how to motivate themselves.
* Chart
    * Users can look at charts to visualize the changes of their emotion over time.


### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Compose
* Chart
* Journal

Optional: 
* Community
* Profile


**Flow Navigation** (Screen to Screen)

- Login/ Sign up -> Dashboard -> Compose -> Emojis & Ratings -> Compose Journal -> Suggestions

- Dashboard -> Detailed Journal

- (Optional) Emojis & Ratings -> Add emojis -> Customize emojis


## Wireframes
<img src="https://i.imgur.com/oITiFAv.png" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models

* Post

| Property | Type | Description |
| -------- | -------- | -------- |
| emotion     | Emotion     | a adjective that describes your present feeling   |
| journal     | String     | users' reflection and comments about their feelings     |
| createdAt     | DateTime     | when the journal was created    |
| suggestion     | Suggestion     | suggestions specific to this post


* Emotion

| Property | Type | Description |
| -------- | -------- | -------- |
| emoji     | String     | an icon to visually describe the emotion      |
| rating     | Number     | the level of how the user is feeling certain emotion on a scale of 1-5    |
| adjective | String | a keyword to describe what emotion that emoji represents


* Suggestion


| Property | Type | Description |
| -------- | -------- | -------- |
| quotation     | String     |  a quote related to the journal/emotions    |
| advice     | String   | useful tips on what to do to get rid of bad emotion or how to keep a positive mindset   |


### Networking
Login Screen
* (Create) Sign up with username and password
* (Get) Sign up with username and password

Past Journal Stream Screen
* (Read/GET) Query all posts where user is author


Compose Screen
* (Create/POST) Create a new post


Suggestions Screen
* (Get) Call from api suggestions related to user emotion/topic (MVP hardcode)

Search for emoji Screen
* (Get) Get emoji related to the keyword

### Optional API:
* [Login using Google](https://developers.google.com/identity/sign-in/android/sign-in)
* [Spotify API](https://developer.spotify.com/documentation/android/quick-start/)
* [Emotion analysis](https://www.twinword.com/api/emotion-analysis.php)
* [Topic extracter](https://www.twinword.com/api/topic-tagging.php)
