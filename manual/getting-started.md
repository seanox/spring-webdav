TODO:

- Methods do not have a fixed signature, the data type of the parameters is
  used as a placeholder and filled when called.
- Methods and the possible placeholder
    - Mapping: Properties, URI, MetaProperties, MetaOutputStream
      expected data type from return value: void
    - Input:  Properties, URI, MetaProperties, MetaInputStream
      expected data type from return value: void
    - Meta: Properties, URI, MetaData
      expected data type from return value: void
    - Attributes: Properties, URI, ApiDavAttribute
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


- Attribute
  For the use of the attributes the following priority exists:
  1. Dynamic value from the attribute-method implementation
  2. Dynamic value from the meta-method implementation
  3. Dynamic value from the annotation expression
  4. Static value from annotation
  5. Default value from the class
- LastModified (default) based on CreationDate and can be null
  LastModified null + CreationDate not null is strange but possible
- ContentLength: Expects Long, alternatively long and string with long value
  null, value less than 0 and (convert) exception suppress output
  default: null
  Exceptions are used like null, but generate an ERROR output in logging
- Accept + ContentLengthMax does not exist as attribute mapping (too special)
- Accept: */* mimetype/* mimetype/mimesubtype */mimesubtype


- Sitemap
- Callback exceptions are logged as errors, but do not interrupt processing
  For attributes, null is then used
- Converter exceptions are logged as errors, but do not interrupt processing
  For attributes, null is then used
- Why the exception behavior:
  If one mapping is broken, all others entries in a folder will not work either.