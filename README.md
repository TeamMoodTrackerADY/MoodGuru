# MoodGuru

## Table of Contents
1. [Overview](#ov)
1. [Product Spec](#ps)
1. [Wireframes](#wf)

<a name="ov"></a>
## Overview
### Description
MoodGuru is an mood-tracking app that utilizes nlp to analyze user's current mood based on their journals, provides customized feedbacks, and records user's mood pattern over time, aiming to improve users' self-awareness about what triggers different emotion and how to culture a healthy mindset. 

Below are two sample usages when a user is happy and angry. 

![Walk-through-joy](https://github.com/TeamMoodTrackerADY/MoodGuru/blob/main/mood_guru_ms2.gif)
![Walk-through-anger](https://media.giphy.com/media/c5qP69vdd1UfkcVABB/giphy.gif)

Liscence warning due to application of [Free liscence of Chaquopy](https://chaquo.com/chaquopy/free-license/) being processed.

For the next milestones, we will work on 
- Community feature allowing users to offer suggestions to one another. 
- Chart to visualize user's mood pattern over time. 
- Topic tagging on user's journal to provide more customized feedbacks. 


### App Evaluation

- **Category:** Emotion, health, informational
- **Mobile:** Instead of a desktop website, it is much more convenient and practical for the users to use their phone to track their mood and journal their daily lives on a mobile app. Whenever the users want to reflect their emotions, they can just do so in a blink of an eye in their own mobile device.
- **Story:** There have been other similar mood tracker Android apps in the market already. (**TODO: Look more into features in Android mood tracker apps!!**)
- **Market:** This app focuses on younger people such as college students and young working adults. But anyone could find this app helpful.
- **Habit:** Since this app's main purpose is to reflect and journal, it could form a healthy habit for the users. If they find suggestions or motivation notes helpful, they would be more likely to use it and gradually form a habit. 
- **Scope:** There is a lot of potential to increase the scope of this app since there are many extra features that can be implemented besides inputting daily moods and journaling. However, as of now, the required user stories should be helpful enough to the users.

<a name="ps"></a>
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


** Flow Navigation** (Screen to Screen)

- Login/ Sign up -> Dashboard -> Compose -> Emojis & Ratings -> Compose Journal -> Suggestions

- Dashboard -> Detailed Journal

- (Optional) Emojis & Ratings -> Add emojis -> Customize emojis

<a name="wf"></a>
## Wireframes
<img src="https://i.imgur.com/oITiFAv.png" width=600>


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

## Open-source libraries used
- [vaderSentiment](https://github.com/cjhutto/vaderSentiment) - NLP entiment analysis tool that scores the polarity and intensity of a sentence/paragraph
- [Chaquopy](https://chaquo.com/chaquopy/) - API for calling Python code in Java/Kotlin.
- [Volley](https://github.com/google/volley) - an HTTP library that makes networking for Android apps easier and, most importantly, faster.
- [Android Async HTTP](https://github.com/codepath/CPAsyncHttpClient) - Simple asynchronous HTTP requests with JSON parsing.
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android.


