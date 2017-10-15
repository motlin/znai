[PlantUml](http://plantuml.com/sequence-diagram) is a text based way of drawing all sort of diagrams.

# In Place

`````markdown-and-result
```plantuml
@startuml
Alice -> Bob: Authentication Request
Bob --> Alice: Authentication Response

Alice -> Bob: Another authentication Request
Alice <-- Bob: another authentication Response
@enduml
```
`````

Consider using [Columns Layout](features/columns) to put your story and a diagram side by side

# External file

If your diagram is stored in a separate file use `:include-plantuml: <path>`.

```columns
left:
    :include-plantuml: demo.plantuml

:include-file: demo.plantuml {title: "demo.plantuml"}
right:
:include-plantuml: demo.plantuml
```