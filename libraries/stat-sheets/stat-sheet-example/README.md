## Simple example MapTool Stat-Sheet Add-On
This repository contains a really simple example of how to create a MapTool Stat-sheet.

### Add-On
Stat-sheets are added to MapTool via an Add-On which have the following structure at minimum:

```
library.json    <-- File that provides the Add-On details
library/        <-- Directory that contains the Add-On contents  
stat_sheet.json <-- File that defines the stat-sheets
```

More details about general Add-Ons can be found at https://docs.rptools.info/docs/add-ons/


### Defining the Stat-Sheet
The stat-sheets are defined in a file called stat_sheets.json, for example
```json
{
  "statSheets": [
    { 
        "name": "Basic",
        "description": "Basic Stat-Sheet",
        "propertyTypes": [ "Basic" ],
        "entry": "sheets/stats/basic/index.hbs" 
    }
  ]
}
```
The propertyTypes field lets MapTool know which property types are allowed to have this stat-sheet, if it is an empty array then ALL property types can use this stat-sheet. In general if you are planning to distribute your stat-sheets outside of a specific framework where you also dictate the name of property types you should not provide any property types here.

The entry field lets MapTool know where in your add-on to find the html file that defines the stat-sheet. The html stat-sheet is actually a handlebars template (more details can be found further below)

As the statSheets field is an array you can define multiple stat-sheets in the same Add-on.

### Creating a Stat Sheet with a handlebars template
Handlebars template are used to create the html which is displayed as the stat-sheet. For more information about handlebars templates see https://handlebarsjs.com/guide

#### Creating the Stat Sheet
Stat Sheets must include the stat-sheet CSS 
```html
    <link
      rel="stylesheet"
      href="lib://net.rptools.maptool/css/mt-stat-sheet.css?cachelib=false"
    />
```
The content of the stat-sheet must also be in a container element with the id of `statSheet` which also has the class `statstatSheetLocation` (it can have other classes as well), for example
```handlebars
<div id="statSheet" class="{{statSheetLocation}}">
  <!-- Stat Sheet Contents go here -->
</div>
```

Doing the above two things will allow MapTool to correctly size and position your stat-sheet as well as set the colours, fonts, etc to match the current theme. If you are going for a custom look you can override the colours, fonts, etc but you should respect the positioning as that is where the user wanted the stat-sheet to be positioned.



#### Values that MapTool will define for the template to use
The following values will define for the handlebars template to use when creating the stat-sheet.
- name - the name of the Token
- gmName - The GM name of the Token (only if player is GM)
- label - The label of the Token
- portraitWidth - The width of the portrait, e.g. 175px. This value is derived by the preference settings of the user so should be respected.
- portraitHeight - The height of the portrait, e.g. 150px. This value is derived by the preference settings of the user so should be respected.
- statSheetLocation - The location of the stat-sheet, this is the class that must be added to the container. It can also be used to make your stat-sheet react to its position, valid values are
  - statSheet-topLeft
  - statSheet-top
  - statSheet-topRight
  - statSheet-right
  - statSheet-bottomRight
  - statSheet-bottom
  - statSheet-bottomLeft
  - statSheet-left
- notes - The token notes
- gmNotes - The token gmNotes (only if player is GM)
- speechName - The token speech bubble name
- tokenType - The token type, PC or NPC
- gm - true if player is GM otherwise false
- propertyList - The token property list, which contains
  - displayName - The name of the property
  - shortName - The short name of the
  - value - The value of the property
  - gmOnly - true if property is marked as gm only in property dialog.

Note: The propertyList respects the settings of the user in the campaign settings. Only the values that are checked as appearing on the stat-sheet will be in this list, and only if they meet the requirements such as owner or GM. This allows the user to still define what they want on the stat-sheet. A handlebars template like the following can be used to display the dynamic properties list (although this is by no means the only way to do so).
```handlebars
<ul class="propertyList">
  {{#each properties}} 
    {{#if this.gmOnly}}
      {{#if this.shortName}}
        <li class="gmProperty">{{this.displayName}}({{this.shortName}}): {{this.value}}</li>
      {{else}}
        <li class="gmProperty">{{this.displayName}}: {{this.value}}</li>
      {{/if}}
    {{else}}
      {{#if this.shortName}}
        <li class="playerProperty">{{this.displayName}}({{this.shortName}}): {{this.value}}</li>
      {{else}}
        <li class="playerProperty">{{this.displayName}}: {{this.value}}</li>
      {{/if}}
    {{/if}} 
  {{/each}}
</ul>
```

#### CSS Variables
Including the stat-sheet CSS will make the following variables available to your CSS (assuming you include the MapTool stat-sheet CSS before yours).
```css
  --mt-theme-color-actions-blue: ...;
  --mt-theme-color-actions-blue-dark: ...;
  --mt-theme-color-actions-green: ...;
  --mt-theme-color-actions-green-dark: ...;
  --mt-theme-color-actions-grey: ...;
  --mt-theme-color-actions-grey-dark: ...;
  --mt-theme-color-actions-grey-inline: ...;
  --mt-theme-color-actions-grey-inline-dark: ...;
  --mt-theme-color-actions-red: ...;
  --mt-theme-color-actions-red-dark: ...;
  --mt-theme-color-actions-yellow: ...;
  --mt-theme-color-actions-yellow-dark: ...;
  --mt-theme-color-objects-black-text: ...;
  --mt-theme-color-objects-blue: ...;
  --mt-theme-color-objects-green: ...;
  --mt-theme-color-objects-green-android: ...;
  --mt-theme-color-objects-grey: ...;
  --mt-theme-color-objects-pink: ...;
  --mt-theme-color-objects-red: ...;
  --mt-theme-color-objects-red-status: ...;
  --mt-theme-color-objects-yellow: ...;
  --mt-theme-color-objects-yellow-dark: ...;

  --mt-theme-font-size: ...;
  --mt-theme-font-family: ...;

  --mt-theme-color-foreground: ...;
  --mt-theme-color-background: ...;
  --mt-theme-color-foreground-disabled: ...;

  --mt-theme-color-button-start-background: ...;
  --mt-theme-color-button-end-background: ...;
  --mt-theme-color-button-foreground: ...;
  --mt-theme-color-button-background-disabled: ...;
  --mt-theme-color-button-foreground-disabled: ...;
  --mt-theme-color-button-pressed-background: ...;
  --mt-theme-color-button-background-hover: ...;
  --mt-theme-color-button-disabled-border-size: ...;
  --mt-theme-color-button-disabled-border-color: ...;

  --mt-theme-color-text-input-foreground: ...;
  --mt-theme-color-text-input-background: ...;
  --mt-theme-color-text-input-disabled-border-size: ...;
  --mt-theme-color-text-input-disabled-border-color: ...;

  --mt-theme-h1-font-family: ...;
  --mt-theme-h1-font-size: ...;

  --mt-theme-h2-font-family: ...;
  --mt-theme-h2-font-size: ...;

  --mt-theme-h3-font-family: ...;
  --mt-theme-h3-font-size: ...;

  --mt-theme-h4-font-family: ...;
  --mt-theme-h4-font-size: ...;

  --mt-theme-h5-font-family: ...;
  --mt-theme-h5-font-size: ...;

  --mt-theme-h6-font-family: ...;
  --mt-theme-h6-font-size: ...;


  --mt-theme-panel-background: ...;
  --mt-theme-panel-foreground: ...;

  --mt-theme-progress-bar-arc: ...;
  --mt-theme-progress-bar-background-color: ...;
  --mt-theme-progress-font-family: ...;
  --mt-theme-progress-font-size: ...;

  --mt-theme-scroll-bar-background-color: ...;
  --mt-theme-scroll-bar-show-buttons: ...;
  --mt-theme-scroll-bar-thumb-highlight-color: ...;
  --mt-theme-scroll-bar-thumb-arc: ...;
  --mt-theme-scroll-bar-thumb-border-color: ...;
  --mt-theme-scroll-bar-thumb-shadow-color: ...;
  --mt-theme-scroll-bar-thumb-dark-shadow-color: ...;
  --mt-theme-scroll-bar-thumb-color: ...;
  --mt-theme-scroll-bar-thumb-insets-left: ...;
  --mt-theme-scroll-bar-thumb-insets-top: ...;
  --mt-theme-scroll-bar-thumb-insets-right: ...;
  --mt-theme-scroll-bar-thumb-insets-bottom: ...;


  font-size: var(--mt-theme-font-size);
  font-family: var(--mt-theme-font-family);
  ```
Which can be used to make your stat elements match the current selected theme in MapTool.