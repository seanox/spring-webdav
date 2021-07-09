TODO:

- Methods do not have a fixed signature, the data type of the parameters is
  used as a placeholder and filled when called.
- Methods and the possible placeholder
    - Mapping: File, Properties, MetaOutputStream
      expected data type from return value: void
    - Input:  File, Properties, MetaInputStream
      expected data type from return value: void
    - Meta: File, Properties, Meta
      expected data type from return value: void
    - Attributes: Properties, File, ApiDavAttribute
      expected data type from return value: depending on the attributes - Boolean, Date, String

    
- Properties: TODO:

  
- Expression Language: Following variables are available: TODO:


- Server status: Throw server status status  
  As a part of the concept: the request processing is finished by throwing a status.


- Validation:
  - in methods of Mapping, Input: properties can be used
  - for Input: attribute Accepted, the CallBack method or the expression can be used


- Permissions
    - Attribute Permitted, the CallBack method or the expression can be used