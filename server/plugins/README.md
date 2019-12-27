These are the plugins that we had to develop. They originally were compiled sep
erately and the main server would scan the jar files for the correct class to l
oad. I haven't made a maven build for these ones. 

The long term plan is to just incorporate these in the main server and not have
them load from external files, since the plugin loading was kind of janky anywa
y.

