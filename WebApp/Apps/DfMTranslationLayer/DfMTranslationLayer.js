function DfMTranslationLayer(){
  var $wnd_0 = window, $doc_0 = document, gwtOnLoad, bodyDone, base = '', metaProps = {}, values = [], providers = [], answers = [], softPermutationId = 0, onLoadErrorFunc, propertyErrorFunc;
  if (!$wnd_0.__gwt_stylesLoaded) {
    $wnd_0.__gwt_stylesLoaded = {};
  }
  if (!$wnd_0.__gwt_scriptsLoaded) {
    $wnd_0.__gwt_scriptsLoaded = {};
  }
  function isHostedMode(){
    var result = false;
    try {
      var query = $wnd_0.location.search;
      return (query.indexOf('gwt.codesvr=') != -1 || (query.indexOf('gwt.hosted=') != -1 || $wnd_0.external && $wnd_0.external.gwtOnLoad)) && query.indexOf('gwt.hybrid') == -1;
    }
     catch (e) {
    }
    isHostedMode = function(){
      return result;
    }
    ;
    return result;
  }

  function maybeStartModule(){
    if (gwtOnLoad && bodyDone) {
      gwtOnLoad(onLoadErrorFunc, 'DfMTranslationLayer', base, softPermutationId);
    }
  }

  function computeScriptBase(){
    var thisScript, markerId = '__gwt_marker_DfMTranslationLayer', markerScript;
    $doc_0.write('<script id="' + markerId + '"><\/script>');
    markerScript = $doc_0.getElementById(markerId);
    thisScript = markerScript && markerScript.previousSibling;
    while (thisScript && thisScript.tagName != 'SCRIPT') {
      thisScript = thisScript.previousSibling;
    }
    function getDirectoryOfFile(path){
      var hashIndex = path.lastIndexOf('#');
      if (hashIndex == -1) {
        hashIndex = path.length;
      }
      var queryIndex = path.indexOf('?');
      if (queryIndex == -1) {
        queryIndex = path.length;
      }
      var slashIndex = path.lastIndexOf('/', Math.min(queryIndex, hashIndex));
      return slashIndex >= 0?path.substring(0, slashIndex + 1):'';
    }

    ;
    if (thisScript && thisScript.src) {
      base = getDirectoryOfFile(thisScript.src);
    }
    if (base == '') {
      var baseElements = $doc_0.getElementsByTagName('base');
      if (baseElements.length > 0) {
        base = baseElements[baseElements.length - 1].href;
      }
       else {
        base = getDirectoryOfFile($doc_0.location.href);
      }
    }
     else if (base.match(/^\w+:\/\//)) {
    }
     else {
      var img = $doc_0.createElement('img');
      img.src = base + 'clear.cache.gif';
      base = getDirectoryOfFile(img.src);
    }
    if (markerScript) {
      markerScript.parentNode.removeChild(markerScript);
    }
  }

  function processMetas(){
    var metas = document.getElementsByTagName('meta');
    for (var i = 0, n = metas.length; i < n; ++i) {
      var meta = metas[i], name_0 = meta.getAttribute('name'), content_0;
      if (name_0) {
        if (name_0 == 'gwt:property') {
          content_0 = meta.getAttribute('content');
          if (content_0) {
            var value, eq = content_0.indexOf('=');
            if (eq >= 0) {
              name_0 = content_0.substring(0, eq);
              value = content_0.substring(eq + 1);
            }
             else {
              name_0 = content_0;
              value = '';
            }
            metaProps[name_0] = value;
          }
        }
         else if (name_0 == 'gwt:onPropertyErrorFn') {
          content_0 = meta.getAttribute('content');
          if (content_0) {
            try {
              propertyErrorFunc = eval(content_0);
            }
             catch (e) {
              alert('Bad handler "' + content_0 + '" for "gwt:onPropertyErrorFn"');
            }
          }
        }
         else if (name_0 == 'gwt:onLoadErrorFn') {
          content_0 = meta.getAttribute('content');
          if (content_0) {
            try {
              onLoadErrorFunc = eval(content_0);
            }
             catch (e) {
              alert('Bad handler "' + content_0 + '" for "gwt:onLoadErrorFn"');
            }
          }
        }
      }
    }
  }

  function unflattenKeylistIntoAnswers(propValArray, value){
    var answer = answers;
    for (var i = 0, n = propValArray.length - 1; i < n; ++i) {
      answer = answer[propValArray[i]] || (answer[propValArray[i]] = []);
    }
    answer[propValArray[n]] = value;
  }

  function computePropValue(propName){
    var value = providers[propName](), allowedValuesMap = values[propName];
    if (value in allowedValuesMap) {
      return value;
    }
    var allowedValuesList = [];
    for (var k in allowedValuesMap) {
      allowedValuesList[allowedValuesMap[k]] = k;
    }
    if (propertyErrorFunc) {
      propertyErrorFunc(propName, allowedValuesList, value);
    }
    throw null;
  }

  providers['user.agent'] = function(){
    var ua = navigator.userAgent.toLowerCase();
    var makeVersion = function(result){
      return parseInt(result[1]) * 1000 + parseInt(result[2]);
    }
    ;
    if (function(){
      return ua.indexOf('opera') != -1;
    }
    ())
      return 'opera';
    if (function(){
      return ua.indexOf('webkit') != -1;
    }
    ())
      return 'safari';
    if (function(){
      return ua.indexOf('msie') != -1 && $doc_0.documentMode >= 9;
    }
    ())
      return 'ie9';
    if (function(){
      return ua.indexOf('msie') != -1 && $doc_0.documentMode >= 8;
    }
    ())
      return 'ie8';
    if (function(){
      var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
      if (result && result.length == 3)
        return makeVersion(result) >= 6000;
    }
    ())
      return 'ie6';
    if (function(){
      return ua.indexOf('gecko') != -1;
    }
    ())
      return 'gecko1_8';
    return 'unknown';
  }
  ;
  values['user.agent'] = {gecko1_8:0, ie6:1, ie8:2, ie9:3, opera:4, safari:5};
  DfMTranslationLayer.onScriptLoad = function(gwtOnLoadFunc){
    DfMTranslationLayer = null;
    gwtOnLoad = gwtOnLoadFunc;
    maybeStartModule();
  }
  ;
  if (isHostedMode()) {
    alert('Single-script hosted mode not yet implemented. See issue ' + 'http://code.google.com/p/google-web-toolkit/issues/detail?id=2079');
    return;
  }
  computeScriptBase();
  processMetas();
  try {
    var strongName;
    unflattenKeylistIntoAnswers(['gecko1_8'], '6DC4DFF7415E0205792D9A9D70A75BDC');
    unflattenKeylistIntoAnswers(['ie6'], '6DC4DFF7415E0205792D9A9D70A75BDC' + ':1');
    unflattenKeylistIntoAnswers(['ie8'], '6DC4DFF7415E0205792D9A9D70A75BDC' + ':2');
    unflattenKeylistIntoAnswers(['ie9'], '6DC4DFF7415E0205792D9A9D70A75BDC' + ':3');
    unflattenKeylistIntoAnswers(['opera'], '6DC4DFF7415E0205792D9A9D70A75BDC' + ':4');
    unflattenKeylistIntoAnswers(['safari'], '6DC4DFF7415E0205792D9A9D70A75BDC' + ':5');
    strongName = answers[computePropValue('user.agent')];
    var idx = strongName.indexOf(':');
    if (idx != -1) {
      softPermutationId = Number(strongName.substring(idx + 1));
    }
  }
   catch (e) {
    return;
  }
  var onBodyDoneTimerId;
  function onBodyDone(){
    if (!bodyDone) {
      bodyDone = true;
      maybeStartModule();
      if ($doc_0.removeEventListener) {
        $doc_0.removeEventListener('DOMContentLoaded', onBodyDone, false);
      }
      if (onBodyDoneTimerId) {
        clearInterval(onBodyDoneTimerId);
      }
    }
  }

  if ($doc_0.addEventListener) {
    $doc_0.addEventListener('DOMContentLoaded', function(){
      onBodyDone();
    }
    , false);
  }
  var onBodyDoneTimerId = setInterval(function(){
    if (/loaded|complete/.test($doc_0.readyState)) {
      onBodyDone();
    }
  }
  , 50);
}

DfMTranslationLayer();
(function () {var $gwt_version = "2.5.1";var $wnd = window;var $doc = $wnd.document;var $moduleName, $moduleBase;var $stats = $wnd.__gwtStatsEvent ? function(a) {$wnd.__gwtStatsEvent(a)} : null;var $strongName = '6DC4DFF7415E0205792D9A9D70A75BDC';var _, P0_longLit = {l:0, m:0, h:0}, seedTable = {}, Q$Object = 0, Q$String = 1, Q$LongLibBase$LongEmul = 2, Q$UserAgentAsserter$UserAgentProperty = 3, Q$LanguageDefinition = 4, Q$ContentDefinition = 5, Q$DictionaryException = 6, Q$StringColourItemTextPart = 7, Q$SearchedWord = 8, Q$SingleTranslation = 9, Q$TextOfLanguage = 10, Q$TranslationParameters = 11, Q$TranslationThreadIF = 12, Q$Normation = 13, Q$IOException = 14, Q$Serializable = 15, Q$CharSequence = 16, Q$Comparable = 17, Q$Exception = 18, Q$Integer = 19, Q$Number = 20, Q$NumberFormatException = 21, Q$StackTraceElement = 22, Q$Throwable = 23, Q$List = 24, Q$Map = 25, Q$Map$Entry = 26, Q$Set = 27, CM$ = {};
function newSeed(id){
  return new seedTable[id];
}

function defineSeed(id, superSeed, castableTypeMap){
  var seed = seedTable[id];
  if (seed && !seed.___clazz$) {
    _ = seed.prototype;
  }
   else {
    !seed && (seed = seedTable[id] = function(){
    }
    );
    _ = seed.prototype = superSeed < 0?{}:newSeed(superSeed);
    _.castableTypeMap$ = castableTypeMap;
  }
  for (var i = 3; i < arguments.length; ++i) {
    arguments[i].prototype = _;
  }
  if (seed.___clazz$) {
    _.___clazz$ = seed.___clazz$;
    seed.___clazz$ = null;
  }
}

function makeCastMap(a){
  var result = {};
  for (var i = 0, c = a.length; i < c; ++i) {
    result[a[i]] = 1;
  }
  return result;
}

function nullMethod(){
}

defineSeed(1, -1, CM$);
_.equals$ = function equals(other){
  return this === other;
}
;
_.getClass$ = function getClass_0(){
  return this.___clazz$;
}
;
_.hashCode$ = function hashCode_0(){
  return getHashCode(this);
}
;
_.toString$ = function toString_0(){
  return this.___clazz$.typeName + '@' + toPowerOfTwoString(this.hashCode$());
}
;
_.toString = function(){
  return this.toString$();
}
;
_.typeMarker$ = nullMethod;
function $printStackTrace(this$static){
  var causeMessage, currentCause, msg;
  msg = new StringBuffer_0;
  currentCause = this$static;
  while (currentCause) {
    causeMessage = currentCause.getMessage();
    currentCause != this$static && (msg.impl.append(msg.data, 'Caused by: ') , msg);
    $append_0(msg, currentCause.___clazz$.typeName);
    msg.impl.append(msg.data, ': ');
    msg.impl.append(msg.data, causeMessage == null?'(No exception detail)':causeMessage);
    msg.impl.append(msg.data, '\n');
    currentCause = currentCause.cause;
  }
}

function $setStackTrace(stackTrace){
  var c, copy, i;
  copy = initDim(_3Ljava_lang_StackTraceElement_2_classLit, makeCastMap([Q$Serializable]), Q$StackTraceElement, stackTrace.length, 0);
  for (i = 0 , c = stackTrace.length; i < c; ++i) {
    if (!stackTrace[i]) {
      throw new NullPointerException_0;
    }
    copy[i] = stackTrace[i];
  }
}

function $toString(this$static){
  var className, msg;
  className = this$static.___clazz$.typeName;
  msg = this$static.getMessage();
  return msg != null?className + ': ' + msg:className;
}

defineSeed(8, 1, makeCastMap([Q$Serializable, Q$Throwable]));
_.getMessage = function getMessage(){
  return this.detailMessage;
}
;
_.toString$ = function toString_1(){
  return $toString(this);
}
;
_.cause = null;
_.detailMessage = null;
function Exception_0(message){
  com_google_gwt_core_client_impl_StackTraceCreator_Collector().fillInStackTrace(this);
  this.detailMessage = message;
}

defineSeed(7, 8, makeCastMap([Q$Serializable, Q$Exception, Q$Throwable]));
function RuntimeException_0(){
  com_google_gwt_core_client_impl_StackTraceCreator_Collector().fillInStackTrace(this);
}

function RuntimeException_1(message){
  Exception_0.call(this, message);
}

defineSeed(6, 7, makeCastMap([Q$Serializable, Q$Exception, Q$Throwable]));
function JavaScriptException_0(e){
  RuntimeException_0.call(this);
  this.e = e;
  this.description = '';
  com_google_gwt_core_client_impl_StackTraceCreator_Collector().createStackTrace(this);
}

function getExceptionDescription(e){
  return instanceOfJso(e)?getExceptionDescription0(dynamicCastJso(e)):e + '';
}

function getExceptionDescription0(e){
  return e == null?null:e.message;
}

function getExceptionName(e){
  var maybeJsoInvocation;
  return e == null?'null':instanceOfJso(e)?getExceptionName0(dynamicCastJso(e)):instanceOf(e, Q$String)?'String':(maybeJsoInvocation = e , isJavaObject(maybeJsoInvocation)?maybeJsoInvocation.___clazz$:Lcom_google_gwt_core_client_JavaScriptObject_2_classLit).typeName;
}

function getExceptionName0(e){
  return e == null?null:e.name;
}

function getExceptionProperties(e){
  return instanceOfJso(e)?getProperties(dynamicCastJso(e)):'';
}

defineSeed(5, 6, makeCastMap([Q$Serializable, Q$Exception, Q$Throwable]), JavaScriptException_0);
_.getMessage = function getMessage_0(){
  this.message_0 == null && (this.name_0 = getExceptionName(this.e) , this.description = this.description + ': ' + getExceptionDescription(this.e) , this.message_0 = '(' + this.name_0 + ') ' + getExceptionProperties(this.e) + this.description , undefined);
  return this.message_0;
}
;
_.description = '';
_.e = null;
_.message_0 = null;
_.name_0 = null;
function equals__devirtual$(this$static, other){
  var maybeJsoInvocation;
  return maybeJsoInvocation = this$static , isJavaObject(maybeJsoInvocation)?maybeJsoInvocation.equals$(other):maybeJsoInvocation === other;
}

function hashCode__devirtual$(this$static){
  var maybeJsoInvocation;
  return maybeJsoInvocation = this$static , isJavaObject(maybeJsoInvocation)?maybeJsoInvocation.hashCode$():getHashCode(maybeJsoInvocation);
}

defineSeed(13, 1, {});
function apply(jsFunction, thisObj, args){
  return jsFunction.apply(thisObj, args);
  var __0;
}

function enter(){
  var now;
  if (entryDepth != 0) {
    now = (new Date).getTime();
    if (now - watchdogEntryDepthLastScheduled > 2000) {
      watchdogEntryDepthLastScheduled = now;
      watchdogEntryDepthTimerId = watchdogEntryDepthSchedule();
    }
  }
  if (entryDepth++ == 0) {
    $flushEntryCommands(($clinit_SchedulerImpl() , INSTANCE));
    return true;
  }
  return false;
}

function entry_0(jsFunction){
  return function(){
    try {
      return entry0(jsFunction, this, arguments);
    }
     catch (e) {
      throw e;
    }
  }
  ;
}

function entry0(jsFunction, thisObj, args){
  var initialEntry;
  initialEntry = enter();
  try {
    return apply(jsFunction, thisObj, args);
  }
   finally {
    exit(initialEntry);
  }
}

function exit(initialEntry){
  initialEntry && $flushFinallyCommands(($clinit_SchedulerImpl() , INSTANCE));
  --entryDepth;
  if (initialEntry) {
    if (watchdogEntryDepthTimerId != -1) {
      watchdogEntryDepthCancel(watchdogEntryDepthTimerId);
      watchdogEntryDepthTimerId = -1;
    }
  }
}

function getHashCode(o){
  return o.$H || (o.$H = ++sNextHashId);
}

function watchdogEntryDepthCancel(timerId){
  $wnd.clearTimeout(timerId);
}

function watchdogEntryDepthSchedule(){
  return $wnd.setTimeout(function(){
    entryDepth != 0 && (entryDepth = 0);
    watchdogEntryDepthTimerId = -1;
  }
  , 10);
}

var entryDepth = 0, sNextHashId = 0, watchdogEntryDepthLastScheduled = 0, watchdogEntryDepthTimerId = -1;
function $clinit_SchedulerImpl(){
  $clinit_SchedulerImpl = nullMethod;
  INSTANCE = new SchedulerImpl_0;
}

function $flushEntryCommands(this$static){
  var oldQueue, rescheduled;
  if (this$static.entryCommands) {
    rescheduled = null;
    do {
      oldQueue = this$static.entryCommands;
      this$static.entryCommands = null;
      rescheduled = runScheduledTasks(oldQueue, rescheduled);
    }
     while (this$static.entryCommands);
    this$static.entryCommands = rescheduled;
  }
}

function $flushFinallyCommands(this$static){
  var oldQueue, rescheduled;
  if (this$static.finallyCommands) {
    rescheduled = null;
    do {
      oldQueue = this$static.finallyCommands;
      this$static.finallyCommands = null;
      rescheduled = runScheduledTasks(oldQueue, rescheduled);
    }
     while (this$static.finallyCommands);
    this$static.finallyCommands = rescheduled;
  }
}

function SchedulerImpl_0(){
}

function push(queue, task){
  !queue && (queue = []);
  queue[queue.length] = task;
  return queue;
}

function runScheduledTasks(tasks, rescheduled){
  var i, j, t;
  for (i = 0 , j = tasks.length; i < j; ++i) {
    t = tasks[i];
    try {
      t[1]?t[0].nullMethod() && (rescheduled = push(rescheduled, t)):t[0].nullMethod();
    }
     catch ($e0) {
      $e0 = caught($e0);
      if (!instanceOf($e0, Q$Throwable))
        throw $e0;
    }
  }
  return rescheduled;
}

defineSeed(15, 13, {}, SchedulerImpl_0);
_.entryCommands = null;
_.finallyCommands = null;
var INSTANCE;
function extractNameFromToString(fnToString){
  var index, start, toReturn;
  toReturn = '';
  fnToString = $trim(fnToString);
  index = fnToString.indexOf('(');
  start = fnToString.indexOf('function') == 0?8:0;
  if (index == -1) {
    index = $indexOf(fnToString, fromCodePoint(64));
    start = fnToString.indexOf('function ') == 0?9:0;
  }
  index != -1 && (toReturn = $trim(fnToString.substr(start, index - start)));
  return toReturn.length > 0?toReturn:'anonymous';
}

function getProperties(e){
  return $getProperties((com_google_gwt_core_client_impl_StackTraceCreator_Collector() , e));
}

function parseInt_0(number){
  return parseInt(number) || -1;
}

function splice(arr, length_0){
  arr.length >= length_0 && arr.splice(0, length_0);
  return arr;
}

function $getProperties(e){
  var result = '';
  try {
    for (var prop in e) {
      if (prop != 'name' && prop != 'message' && prop != 'toString') {
        try {
          result += '\n ' + prop + ': ' + e[prop];
        }
         catch (ignored) {
        }
      }
    }
  }
   catch (ignored) {
  }
  return result;
}

function $makeException(){
  try {
    null.a();
  }
   catch (e) {
    return e;
  }
}

function StackTraceCreator$Collector_0(){
}

defineSeed(18, 1, {}, StackTraceCreator$Collector_0);
_.collect = function collect(){
  var seen = {};
  var toReturn = [];
  var callee = arguments.callee.caller.caller;
  while (callee) {
    var name_0 = this.extractName(callee.toString());
    toReturn.push(name_0);
    var keyName = ':' + name_0;
    var withThisName = seen[keyName];
    if (withThisName) {
      var i, j;
      for (i = 0 , j = withThisName.length; i < j; i++) {
        if (withThisName[i] === callee) {
          return toReturn;
        }
      }
    }
    (withThisName || (seen[keyName] = [])).push(callee);
    callee = callee.caller;
  }
  return toReturn;
}
;
_.createStackTrace = function createStackTrace(e){
  var i, j, stack, stackTrace;
  stack = this.inferFrom(instanceOfJso(e.e)?dynamicCastJso(e.e):null);
  stackTrace = initDim(_3Ljava_lang_StackTraceElement_2_classLit, makeCastMap([Q$Serializable]), Q$StackTraceElement, stack.length, 0);
  for (i = 0 , j = stackTrace.length; i < j; ++i) {
    stackTrace[i] = new StackTraceElement_0(stack[i], null, -1);
  }
  $setStackTrace(stackTrace);
}
;
_.extractName = function extractName(fnToString){
  return extractNameFromToString(fnToString);
}
;
_.fillInStackTrace = function fillInStackTrace(t){
  var i, j, stack, stackTrace;
  stack = com_google_gwt_core_client_impl_StackTraceCreator_Collector().collect();
  stackTrace = initDim(_3Ljava_lang_StackTraceElement_2_classLit, makeCastMap([Q$Serializable]), Q$StackTraceElement, stack.length, 0);
  for (i = 0 , j = stackTrace.length; i < j; ++i) {
    stackTrace[i] = new StackTraceElement_0(stack[i], null, -1);
  }
  $setStackTrace(stackTrace);
}
;
_.inferFrom = function inferFrom(e){
  return [];
}
;
function $inferFrom(this$static, e){
  var i, j, stack;
  stack = this$static.getStack(e);
  for (i = 0 , j = stack.length; i < j; ++i) {
    stack[i] = this$static.extractName(stack[i]);
  }
  return stack;
}

function StackTraceCreator$CollectorMoz_0(){
}

defineSeed(20, 18, {}, StackTraceCreator$CollectorMoz_0);
_.collect = function collect_0(){
  return splice(this.inferFrom($makeException()), this.toSplice());
}
;
_.getStack = function getStack(e){
  return e && e.stack?e.stack.split('\n'):[];
}
;
_.inferFrom = function inferFrom_0(e){
  return $inferFrom(this, e);
}
;
_.toSplice = function toSplice(){
  return 2;
}
;
function $clinit_StackTraceCreator$CollectorChrome(){
  $clinit_StackTraceCreator$CollectorChrome = nullMethod;
  Error.stackTraceLimit = 128;
}

function $inferFrom_0(this$static, e){
  var stack;
  stack = $inferFrom(this$static, e);
  return stack.length == 0?(new StackTraceCreator$Collector_0).inferFrom(e):splice(stack, 1);
}

function $parseStackTrace(this$static, stack){
  var col, endFileUrl, fileName, i, j, lastColon, line, location_0, stackElements, stackTrace;
  stackTrace = initDim(_3Ljava_lang_StackTraceElement_2_classLit, makeCastMap([Q$Serializable]), Q$StackTraceElement, stack.length, 0);
  for (i = 0 , j = stackTrace.length; i < j; ++i) {
    stackElements = $split(stack[i], '@@', 0);
    line = -1;
    col = -1;
    fileName = 'Unknown';
    if (stackElements.length == 2 && stackElements[1] != null) {
      location_0 = stackElements[1];
      lastColon = $lastIndexOf(location_0, fromCodePoint(58));
      endFileUrl = $lastIndexOf_0(location_0, fromCodePoint(58), lastColon - 1);
      fileName = location_0.substr(0, endFileUrl - 0);
      if (lastColon != -1 && endFileUrl != -1) {
        line = parseInt_0(location_0.substr(endFileUrl + 1, lastColon - (endFileUrl + 1)));
        col = parseInt_0($substring(location_0, lastColon + 1));
      }
    }
    stackTrace[i] = new StackTraceElement_0(stackElements[0], fileName + '@' + col, this$static.replaceIfNoSourceMap(line < 0?-1:line));
  }
  $setStackTrace(stackTrace);
}

defineSeed(19, 20, {});
_.collect = function collect_1(){
  var res;
  res = splice($inferFrom_0(this, $makeException()), 3);
  res.length == 0 && (res = splice((new StackTraceCreator$Collector_0).collect(), 1));
  return res;
}
;
_.createStackTrace = function createStackTrace_0(e){
  var stack;
  stack = $inferFrom_0(this, instanceOfJso(e.e)?dynamicCastJso(e.e):null);
  $parseStackTrace(this, stack);
}
;
_.extractName = function extractName_0(fnToString){
  var closeParen, index, location_0, toReturn;
  if (fnToString.length == 0) {
    return 'anonymous';
  }
  toReturn = $trim(fnToString);
  toReturn.indexOf('at ') == 0 && (toReturn = $substring(toReturn, 3));
  index = toReturn.indexOf('[');
  index != -1 && (toReturn = $trim(toReturn.substr(0, index - 0)) + $trim($substring(toReturn, toReturn.indexOf(']', index) + 1)));
  index = toReturn.indexOf('(');
  if (index == -1) {
    index = toReturn.indexOf('@');
    if (index == -1) {
      location_0 = toReturn;
      toReturn = '';
    }
     else {
      location_0 = $trim($substring(toReturn, index + 1));
      toReturn = $trim(toReturn.substr(0, index - 0));
    }
  }
   else {
    closeParen = toReturn.indexOf(')', index);
    location_0 = toReturn.substr(index + 1, closeParen - (index + 1));
    toReturn = $trim(toReturn.substr(0, index - 0));
  }
  index = $indexOf(toReturn, fromCodePoint(46));
  index != -1 && (toReturn = $substring(toReturn, index + 1));
  return (toReturn.length > 0?toReturn:'anonymous') + '@@' + location_0;
}
;
_.fillInStackTrace = function fillInStackTrace_0(t){
  var stack;
  stack = com_google_gwt_core_client_impl_StackTraceCreator_Collector().collect();
  $parseStackTrace(this, stack);
}
;
_.inferFrom = function inferFrom_1(e){
  return $inferFrom_0(this, e);
}
;
_.replaceIfNoSourceMap = function replaceIfNoSourceMap(line){
  return line;
}
;
_.toSplice = function toSplice_0(){
  return 3;
}
;
function StackTraceCreator$CollectorChromeNoSourceMap_0(){
  $clinit_StackTraceCreator$CollectorChrome();
}

defineSeed(21, 19, {}, StackTraceCreator$CollectorChromeNoSourceMap_0);
_.replaceIfNoSourceMap = function replaceIfNoSourceMap_0(line){
  return -1;
}
;
function StackTraceCreator$CollectorOpera_0(){
}

defineSeed(22, 20, {}, StackTraceCreator$CollectorOpera_0);
_.extractName = function extractName_1(fnToString){
  return fnToString.length == 0?'anonymous':fnToString;
}
;
_.getStack = function getStack_0(e){
  var i, i2, idx, j, toReturn;
  toReturn = e && e.message?e.message.split('\n'):[];
  for (i = 0 , i2 = 0 , j = toReturn.length; i2 < j; ++i , i2 += 2) {
    idx = toReturn[i2].lastIndexOf('function ');
    idx == -1?(toReturn[i] = '' , undefined):(toReturn[i] = $trim($substring(toReturn[i2], idx + 9)) , undefined);
  }
  toReturn.length = i;
  return toReturn;
}
;
_.toSplice = function toSplice_1(){
  return 3;
}
;
defineSeed(23, 1, {});
function StringBufferImplAppend_0(){
}

defineSeed(24, 23, {}, StringBufferImplAppend_0);
_.append = function append(data, x){
  this.string += x;
}
;
_.appendNonNull = function appendNonNull(data, x){
  this.string += x;
}
;
_.createData = function createData(){
  return null;
}
;
_.length_0 = function length_1(data){
  return this.string.length;
}
;
_.replace_0 = function replace(data, start, end, toInsert){
  this.string = $substring_0(this.string, 0, start) + toInsert + $substring(this.string, end);
}
;
_.toString_0 = function toString_2(data){
  return this.string;
}
;
_.string = '';
function $appendNonNull(a, x){
  a[a.explicitLength++] = x;
}

function $takeString(a){
  var s = a.join('');
  a.length = a.explicitLength = 0;
  return s;
}

function $toString_0(this$static, a){
  var s;
  s = $takeString(a);
  $appendNonNull(a, s);
  return s;
}

defineSeed(26, 23, {});
_.append = function append_0(a, x){
  a[a.explicitLength++] = x == null?'null':x;
}
;
_.appendNonNull = function appendNonNull_0(a, x){
  $appendNonNull(a, x);
}
;
_.createData = function createData_0(){
  var array = [];
  array.explicitLength = 0;
  return array;
}
;
_.length_0 = function length_2(a){
  return $toString_0(this, a).length;
}
;
_.replace_0 = function replace_0(a, start, end, toInsert){
  var s;
  s = $takeString(a);
  $appendNonNull(a, s.substr(0, start - 0));
  a[a.explicitLength++] = toInsert == null?'null':toInsert;
  $appendNonNull(a, $substring(s, end));
}
;
_.toString_0 = function toString_3(a){
  return $toString_0(this, a);
}
;
function StringBufferImplArray_0(){
}

defineSeed(25, 26, {}, StringBufferImplArray_0);
function Array_0(){
}

function createFromSeed(seedType, length_0){
  var array = new Array(length_0);
  if (seedType == 3) {
    for (var i = 0; i < length_0; ++i) {
      var value = new Object;
      value.l = value.m = value.h = 0;
      array[i] = value;
    }
  }
   else if (seedType > 0) {
    var value = [null, 0, false][seedType];
    for (var i = 0; i < length_0; ++i) {
      array[i] = value;
    }
  }
  return array;
}

function initDim(arrayClass, castableTypeMap, queryId, length_0, seedType){
  var result;
  result = createFromSeed(seedType, length_0);
  initValues(arrayClass, castableTypeMap, queryId, result);
  return result;
}

function initValues(arrayClass, castableTypeMap, queryId, array){
  $clinit_Array$ExpandoWrapper();
  wrapArray(array, expandoNames_0, expandoValues_0);
  array.___clazz$ = arrayClass;
  array.castableTypeMap$ = castableTypeMap;
  array.queryId$ = queryId;
  return array;
}

function setCheck(array, index, value){
  if (value != null) {
    if (array.queryId$ > 0 && !canCastUnsafe(value, array.queryId$)) {
      throw new ArrayStoreException_0;
    }
     else if (array.queryId$ == -1 && (value.typeMarker$ == nullMethod || canCast(value, 1))) {
      throw new ArrayStoreException_0;
    }
     else if (array.queryId$ < -1 && !(value.typeMarker$ != nullMethod && !canCast(value, 1)) && !canCastUnsafe(value, -array.queryId$)) {
      throw new ArrayStoreException_0;
    }
  }
  return array[index] = value;
}

defineSeed(29, 1, {}, Array_0);
_.queryId$ = 0;
function $clinit_Array$ExpandoWrapper(){
  $clinit_Array$ExpandoWrapper = nullMethod;
  expandoNames_0 = [];
  expandoValues_0 = [];
  initExpandos(new Array_0, expandoNames_0, expandoValues_0);
}

function initExpandos(protoType, expandoNames, expandoValues){
  var i = 0, value;
  for (var name_0 in protoType) {
    if (value = protoType[name_0]) {
      expandoNames[i] = name_0;
      expandoValues[i] = value;
      ++i;
    }
  }
}

function wrapArray(array, expandoNames, expandoValues){
  $clinit_Array$ExpandoWrapper();
  for (var i = 0, c = expandoNames.length; i < c; ++i) {
    array[expandoNames[i]] = expandoValues[i];
  }
}

var expandoNames_0, expandoValues_0;
function canCast(src, dstId){
  return src.castableTypeMap$ && !!src.castableTypeMap$[dstId];
}

function canCastUnsafe(src, dstId){
  return src.castableTypeMap$ && src.castableTypeMap$[dstId];
}

function dynamicCast(src, dstId){
  if (src != null && !canCastUnsafe(src, dstId)) {
    throw new ClassCastException_0;
  }
  return src;
}

function dynamicCastJso(src){
  if (src != null && (src.typeMarker$ == nullMethod || canCast(src, 1))) {
    throw new ClassCastException_0;
  }
  return src;
}

function instanceOf(src, dstId){
  return src != null && canCast(src, dstId);
}

function instanceOfJso(src){
  return src != null && src.typeMarker$ != nullMethod && !canCast(src, 1);
}

function isJavaObject(src){
  return src.typeMarker$ == nullMethod || canCast(src, 1);
}

function maskUndefined(src){
  return src == null?null:src;
}

function round_int(x){
  return ~~Math.max(Math.min(x, 2147483647), -2147483648);
}

function com_google_gwt_core_client_impl_StackTraceCreator_Collector(){
  switch (permutationId) {
    case 0:
      return new StackTraceCreator$CollectorMoz_0;
    case 4:
      return new StackTraceCreator$CollectorOpera_0;
    case 5:
      return new StackTraceCreator$CollectorChromeNoSourceMap_0;
  }
  return new StackTraceCreator$Collector_0;
}

function com_google_gwt_core_client_impl_StringBufferImpl(){
  switch (permutationId) {
    case 1:
    case 2:
    case 3:
      return new StringBufferImplArray_0;
  }
  return new StringBufferImplAppend_0;
}

function com_google_gwt_useragent_client_UserAgentAsserter_UserAgentProperty(){
  switch (permutationId) {
    case 1:
      return new UserAgentAsserter_UserAgentPropertyImplIe6_0;
    case 2:
      return new UserAgentAsserter_UserAgentPropertyImplIe8_0;
    case 3:
      return new UserAgentAsserter_UserAgentPropertyImplIe9_0;
    case 4:
      return new UserAgentAsserter_UserAgentPropertyImplOpera_0;
    case 5:
      return new UserAgentAsserter_UserAgentPropertyImplSafari_0;
  }
  return new UserAgentAsserter_UserAgentPropertyImplGecko1_8_0;
}

var permutationId = -1;
function init(){
  !!$stats && onModuleStart('com.google.gwt.useragent.client.UserAgentAsserter');
  $onModuleLoad_0();
  !!$stats && onModuleStart('com.google.gwt.user.client.DocumentModeAsserter');
  $onModuleLoad();
  !!$stats && onModuleStart('de.kugihan.dictionaryformids.client.TranslationLayerGWT');
  $onModuleLoad_1(new TranslationLayerGWT_0);
}

function caught(e){
  if (instanceOf(e, Q$Throwable)) {
    return e;
  }
  return new JavaScriptException_0(e);
}

function create(value){
  var a0, a1, a2;
  a0 = value & 4194303;
  a1 = ~~value >> 22 & 4194303;
  a2 = value < 0?1048575:0;
  return create0(a0, a1, a2);
}

function create_0(a){
  return create0(a.l, a.m, a.h);
}

function create0(l_0, m_0, h_0){
  return _ = new LongLibBase$LongEmul_0 , _.l = l_0 , _.m = m_0 , _.h = h_0 , _;
}

function divMod(a, b, computeRemainder){
  var aIsCopy, aIsMinValue, aIsNegative, bpower, c, negative;
  if (b.l == 0 && b.m == 0 && b.h == 0) {
    throw new ArithmeticException_0;
  }
  if (a.l == 0 && a.m == 0 && a.h == 0) {
    computeRemainder && (remainder = create0(0, 0, 0));
    return create0(0, 0, 0);
  }
  if (b.h == 524288 && b.m == 0 && b.l == 0) {
    return divModByMinValue(a, computeRemainder);
  }
  negative = false;
  if (~~b.h >> 19 != 0) {
    b = neg(b);
    negative = true;
  }
  bpower = powerOfTwo(b);
  aIsNegative = false;
  aIsMinValue = false;
  aIsCopy = false;
  if (a.h == 524288 && a.m == 0 && a.l == 0) {
    aIsMinValue = true;
    aIsNegative = true;
    if (bpower == -1) {
      a = create_0(($clinit_LongLib$Const() , MAX_VALUE));
      aIsCopy = true;
      negative = !negative;
    }
     else {
      c = shr(a, bpower);
      negative && negate(c);
      computeRemainder && (remainder = create0(0, 0, 0));
      return c;
    }
  }
   else if (~~a.h >> 19 != 0) {
    aIsNegative = true;
    a = neg(a);
    aIsCopy = true;
    negative = !negative;
  }
  if (bpower != -1) {
    return divModByShift(a, bpower, negative, aIsNegative, computeRemainder);
  }
  if (!gte_0(a, b)) {
    computeRemainder && (aIsNegative?(remainder = neg(a)):(remainder = create0(a.l, a.m, a.h)));
    return create0(0, 0, 0);
  }
  return divModHelper(aIsCopy?a:create0(a.l, a.m, a.h), b, negative, aIsNegative, aIsMinValue, computeRemainder);
}

function divModByMinValue(a, computeRemainder){
  if (a.h == 524288 && a.m == 0 && a.l == 0) {
    computeRemainder && (remainder = create0(0, 0, 0));
    return create_0(($clinit_LongLib$Const() , ONE));
  }
  computeRemainder && (remainder = create0(a.l, a.m, a.h));
  return create0(0, 0, 0);
}

function divModByShift(a, bpower, negative, aIsNegative, computeRemainder){
  var c;
  c = shr(a, bpower);
  negative && negate(c);
  if (computeRemainder) {
    a = maskRight(a, bpower);
    aIsNegative?(remainder = neg(a)):(remainder = create0(a.l, a.m, a.h));
  }
  return c;
}

function divModHelper(a, b, negative, aIsNegative, aIsMinValue, computeRemainder){
  var bshift, gte, quotient, shift, a1, a2, a0;
  shift = numberOfLeadingZeros(b) - numberOfLeadingZeros(a);
  bshift = shl(b, shift);
  quotient = create0(0, 0, 0);
  while (shift >= 0) {
    gte = trialSubtract(a, bshift);
    if (gte) {
      shift < 22?(quotient.l |= 1 << shift , undefined):shift < 44?(quotient.m |= 1 << shift - 22 , undefined):(quotient.h |= 1 << shift - 44 , undefined);
      if (a.l == 0 && a.m == 0 && a.h == 0) {
        break;
      }
    }
    a1 = bshift.m;
    a2 = bshift.h;
    a0 = bshift.l;
    bshift.h = ~~a2 >>> 1;
    bshift.m = ~~a1 >>> 1 | (a2 & 1) << 21;
    bshift.l = ~~a0 >>> 1 | (a1 & 1) << 21;
    --shift;
  }
  negative && negate(quotient);
  if (computeRemainder) {
    if (aIsNegative) {
      remainder = neg(a);
      aIsMinValue && (remainder = sub_0(remainder, ($clinit_LongLib$Const() , ONE)));
    }
     else {
      remainder = create0(a.l, a.m, a.h);
    }
  }
  return quotient;
}

function maskRight(a, bits){
  var b0, b1, b2;
  if (bits <= 22) {
    b0 = a.l & (1 << bits) - 1;
    b1 = b2 = 0;
  }
   else if (bits <= 44) {
    b0 = a.l;
    b1 = a.m & (1 << bits - 22) - 1;
    b2 = 0;
  }
   else {
    b0 = a.l;
    b1 = a.m;
    b2 = a.h & (1 << bits - 44) - 1;
  }
  return create0(b0, b1, b2);
}

function negate(a){
  var neg0, neg1, neg2;
  neg0 = ~a.l + 1 & 4194303;
  neg1 = ~a.m + (neg0 == 0?1:0) & 4194303;
  neg2 = ~a.h + (neg0 == 0 && neg1 == 0?1:0) & 1048575;
  a.l = neg0;
  a.m = neg1;
  a.h = neg2;
}

function numberOfLeadingZeros(a){
  var b1, b2;
  b2 = numberOfLeadingZeros_0(a.h);
  if (b2 == 32) {
    b1 = numberOfLeadingZeros_0(a.m);
    return b1 == 32?numberOfLeadingZeros_0(a.l) + 32:b1 + 20 - 10;
  }
   else {
    return b2 - 12;
  }
}

function powerOfTwo(a){
  var h_0, l_0, m_0;
  l_0 = a.l;
  if ((l_0 & l_0 - 1) != 0) {
    return -1;
  }
  m_0 = a.m;
  if ((m_0 & m_0 - 1) != 0) {
    return -1;
  }
  h_0 = a.h;
  if ((h_0 & h_0 - 1) != 0) {
    return -1;
  }
  if (h_0 == 0 && m_0 == 0 && l_0 == 0) {
    return -1;
  }
  if (h_0 == 0 && m_0 == 0 && l_0 != 0) {
    return numberOfTrailingZeros(l_0);
  }
  if (h_0 == 0 && m_0 != 0 && l_0 == 0) {
    return numberOfTrailingZeros(m_0) + 22;
  }
  if (h_0 != 0 && m_0 == 0 && l_0 == 0) {
    return numberOfTrailingZeros(h_0) + 44;
  }
  return -1;
}

function trialSubtract(a, b){
  var sum0, sum1, sum2;
  sum2 = a.h - b.h;
  if (sum2 < 0) {
    return false;
  }
  sum0 = a.l - b.l;
  sum1 = a.m - b.m + (~~sum0 >> 22);
  sum2 += ~~sum1 >> 22;
  if (sum2 < 0) {
    return false;
  }
  a.l = sum0 & 4194303;
  a.m = sum1 & 4194303;
  a.h = sum2 & 1048575;
  return true;
}

var remainder = null;
function add(a, b){
  var sum0, sum1, sum2;
  sum0 = a.l + b.l;
  sum1 = a.m + b.m + (~~sum0 >> 22);
  sum2 = a.h + b.h + (~~sum1 >> 22);
  return create0(sum0 & 4194303, sum1 & 4194303, sum2 & 1048575);
}

function fromDouble(value){
  var a0, a1, a2, negative, result;
  if (isNaN(value)) {
    return $clinit_LongLib$Const() , ZERO;
  }
  if (value < -9223372036854775808) {
    return $clinit_LongLib$Const() , MIN_VALUE;
  }
  if (value >= 9223372036854775807) {
    return $clinit_LongLib$Const() , MAX_VALUE;
  }
  negative = false;
  if (value < 0) {
    negative = true;
    value = -value;
  }
  a2 = 0;
  if (value >= 17592186044416) {
    a2 = round_int(value / 17592186044416);
    value -= a2 * 17592186044416;
  }
  a1 = 0;
  if (value >= 4194304) {
    a1 = round_int(value / 4194304);
    value -= a1 * 4194304;
  }
  a0 = round_int(value);
  result = create0(a0, a1, a2);
  negative && negate(result);
  return result;
}

function fromInt(value){
  var rebase, result;
  if (value > -129 && value < 128) {
    rebase = value + 128;
    boxedValues == null && (boxedValues = initDim(_3Lcom_google_gwt_lang_LongLibBase$LongEmul_2_classLit, makeCastMap([Q$Serializable]), Q$LongLibBase$LongEmul, 256, 0));
    result = boxedValues[rebase];
    !result && (result = boxedValues[rebase] = create(value));
    return result;
  }
  return create(value);
}

function gt(a, b){
  var signa, signb;
  signa = ~~a.h >> 19;
  signb = ~~b.h >> 19;
  return signa == 0?signb != 0 || a.h > b.h || a.h == b.h && a.m > b.m || a.h == b.h && a.m == b.m && a.l > b.l:!(signb == 0 || a.h < b.h || a.h == b.h && a.m < b.m || a.h == b.h && a.m == b.m && a.l <= b.l);
}

function gte_0(a, b){
  var signa, signb;
  signa = ~~a.h >> 19;
  signb = ~~b.h >> 19;
  return signa == 0?signb != 0 || a.h > b.h || a.h == b.h && a.m > b.m || a.h == b.h && a.m == b.m && a.l >= b.l:!(signb == 0 || a.h < b.h || a.h == b.h && a.m < b.m || a.h == b.h && a.m == b.m && a.l < b.l);
}

function lt(a, b){
  return !gte_0(a, b);
}

function neg(a){
  var neg0, neg1, neg2;
  neg0 = ~a.l + 1 & 4194303;
  neg1 = ~a.m + (neg0 == 0?1:0) & 4194303;
  neg2 = ~a.h + (neg0 == 0 && neg1 == 0?1:0) & 1048575;
  return create0(neg0, neg1, neg2);
}

function neq(a, b){
  return a.l != b.l || a.m != b.m || a.h != b.h;
}

function shl(a, n){
  var res0, res1, res2;
  n &= 63;
  if (n < 22) {
    res0 = a.l << n;
    res1 = a.m << n | ~~a.l >> 22 - n;
    res2 = a.h << n | ~~a.m >> 22 - n;
  }
   else if (n < 44) {
    res0 = 0;
    res1 = a.l << n - 22;
    res2 = a.m << n - 22 | ~~a.l >> 44 - n;
  }
   else {
    res0 = 0;
    res1 = 0;
    res2 = a.l << n - 44;
  }
  return create0(res0 & 4194303, res1 & 4194303, res2 & 1048575);
}

function shr(a, n){
  var a2, negative, res0, res1, res2;
  n &= 63;
  a2 = a.h;
  negative = (a2 & 524288) != 0;
  negative && (a2 |= -1048576);
  if (n < 22) {
    res2 = ~~a2 >> n;
    res1 = ~~a.m >> n | a2 << 22 - n;
    res0 = ~~a.l >> n | a.m << 22 - n;
  }
   else if (n < 44) {
    res2 = negative?1048575:0;
    res1 = ~~a2 >> n - 22;
    res0 = ~~a.m >> n - 22 | a2 << 44 - n;
  }
   else {
    res2 = negative?1048575:0;
    res1 = negative?4194303:0;
    res0 = ~~a2 >> n - 44;
  }
  return create0(res0 & 4194303, res1 & 4194303, res2 & 1048575);
}

function sub_0(a, b){
  var sum0, sum1, sum2;
  sum0 = a.l - b.l;
  sum1 = a.m - b.m + (~~sum0 >> 22);
  sum2 = a.h - b.h + (~~sum1 >> 22);
  return create0(sum0 & 4194303, sum1 & 4194303, sum2 & 1048575);
}

function toInt(a){
  return a.l | a.m << 22;
}

function toString_4(a){
  var digits, rem, res, tenPowerLong, zeroesNeeded;
  if (a.l == 0 && a.m == 0 && a.h == 0) {
    return '0';
  }
  if (a.h == 524288 && a.m == 0 && a.l == 0) {
    return '-9223372036854775808';
  }
  if (~~a.h >> 19 != 0) {
    return '-' + toString_4(neg(a));
  }
  rem = a;
  res = '';
  while (!(rem.l == 0 && rem.m == 0 && rem.h == 0)) {
    tenPowerLong = fromInt(1000000000);
    rem = divMod(rem, tenPowerLong, true);
    digits = '' + toInt(remainder);
    if (!(rem.l == 0 && rem.m == 0 && rem.h == 0)) {
      zeroesNeeded = 9 - digits.length;
      for (; zeroesNeeded > 0; --zeroesNeeded) {
        digits = '0' + digits;
      }
    }
    res = digits + res;
  }
  return res;
}

var boxedValues = null;
function $clinit_LongLib$Const(){
  $clinit_LongLib$Const = nullMethod;
  MAX_VALUE = create0(4194303, 4194303, 524287);
  MIN_VALUE = create0(0, 0, 524288);
  ONE = fromInt(1);
  fromInt(2);
  ZERO = fromInt(0);
}

var MAX_VALUE, MIN_VALUE, ONE, ZERO;
function LongLibBase$LongEmul_0(){
}

defineSeed(39, 1, makeCastMap([Q$LongLibBase$LongEmul]), LongLibBase$LongEmul_0);
function onModuleStart(mainClassName){
  return $stats({moduleName:$moduleName, sessionId:$sessionId, subSystem:'startup', evtGroup:'moduleStartup', millis:(new Date).getTime(), type:'onModuleLoadStart', className:mainClassName});
}

function $onModuleLoad(){
  var allowedModes, currentMode, i;
  currentMode = $doc.compatMode;
  allowedModes = initValues(_3Ljava_lang_String_2_classLit, makeCastMap([Q$Serializable]), Q$String, ['CSS1Compat']);
  for (i = 0; i < allowedModes.length; ++i) {
    if ($equals(allowedModes[i], currentMode)) {
      return;
    }
  }
  allowedModes.length == 1 && $equals('CSS1Compat', allowedModes[0]) && $equals('BackCompat', currentMode)?"GWT no longer supports Quirks Mode (document.compatMode=' BackCompat').<br>Make sure your application's host HTML page has a Standards Mode (document.compatMode=' CSS1Compat') doctype,<br>e.g. by using &lt;!doctype html&gt; at the start of your application's HTML page.<br><br>To continue using this unsupported rendering mode and risk layout problems, suppress this message by adding<br>the following line to your*.gwt.xml module file:<br>&nbsp;&nbsp;&lt;extend-configuration-property name=\"document.compatMode\" value=\"" + currentMode + '"/&gt;':"Your *.gwt.xml module configuration prohibits the use of the current doucment rendering mode (document.compatMode=' " + currentMode + "').<br>Modify your application's host HTML page doctype, or update your custom 'document.compatMode' configuration property settings.";
}

function $onModuleLoad_0(){
  var compileTimeValue, impl, runtimeValue;
  impl = dynamicCast(com_google_gwt_useragent_client_UserAgentAsserter_UserAgentProperty(), Q$UserAgentAsserter$UserAgentProperty);
  if (!impl.getUserAgentRuntimeWarning()) {
    return;
  }
  compileTimeValue = impl.getCompileTimeValue();
  runtimeValue = impl.getRuntimeValue();
  $equals(compileTimeValue, runtimeValue) || ($wnd.alert('ERROR: Possible problem with your *.gwt.xml module file.\nThe compile time user.agent value (' + compileTimeValue + ') does not match the runtime user.agent value (' + runtimeValue + '). Expect more errors.\n') , undefined);
}

function UserAgentAsserter_UserAgentPropertyImplGecko1_8_0(){
}

defineSeed(45, 1, makeCastMap([Q$UserAgentAsserter$UserAgentProperty]), UserAgentAsserter_UserAgentPropertyImplGecko1_8_0);
_.getCompileTimeValue = function getCompileTimeValue(){
  return 'gecko1_8';
}
;
_.getRuntimeValue = function getRuntimeValue(){
  var ua = navigator.userAgent.toLowerCase();
  var makeVersion = function(result){
    return parseInt(result[1]) * 1000 + parseInt(result[2]);
  }
  ;
  if (function(){
    return ua.indexOf('opera') != -1;
  }
  ())
    return 'opera';
  if (function(){
    return ua.indexOf('webkit') != -1;
  }
  ())
    return 'safari';
  if (function(){
    return ua.indexOf('msie') != -1 && $doc.documentMode >= 9;
  }
  ())
    return 'ie9';
  if (function(){
    return ua.indexOf('msie') != -1 && $doc.documentMode >= 8;
  }
  ())
    return 'ie8';
  if (function(){
    var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
    if (result && result.length == 3)
      return makeVersion(result) >= 6000;
  }
  ())
    return 'ie6';
  if (function(){
    return ua.indexOf('gecko') != -1;
  }
  ())
    return 'gecko1_8';
  return 'unknown';
}
;
_.getUserAgentRuntimeWarning = function getUserAgentRuntimeWarning(){
  return true;
}
;
function UserAgentAsserter_UserAgentPropertyImplIe6_0(){
}

defineSeed(46, 1, makeCastMap([Q$UserAgentAsserter$UserAgentProperty]), UserAgentAsserter_UserAgentPropertyImplIe6_0);
_.getCompileTimeValue = function getCompileTimeValue_0(){
  return 'ie6';
}
;
_.getRuntimeValue = function getRuntimeValue_0(){
  var ua = navigator.userAgent.toLowerCase();
  var makeVersion = function(result){
    return parseInt(result[1]) * 1000 + parseInt(result[2]);
  }
  ;
  if (function(){
    return ua.indexOf('opera') != -1;
  }
  ())
    return 'opera';
  if (function(){
    return ua.indexOf('webkit') != -1;
  }
  ())
    return 'safari';
  if (function(){
    return ua.indexOf('msie') != -1 && $doc.documentMode >= 9;
  }
  ())
    return 'ie9';
  if (function(){
    return ua.indexOf('msie') != -1 && $doc.documentMode >= 8;
  }
  ())
    return 'ie8';
  if (function(){
    var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
    if (result && result.length == 3)
      return makeVersion(result) >= 6000;
  }
  ())
    return 'ie6';
  if (function(){
    return ua.indexOf('gecko') != -1;
  }
  ())
    return 'gecko1_8';
  return 'unknown';
}
;
_.getUserAgentRuntimeWarning = function getUserAgentRuntimeWarning_0(){
  return true;
}
;
function UserAgentAsserter_UserAgentPropertyImplIe8_0(){
}

defineSeed(47, 1, makeCastMap([Q$UserAgentAsserter$UserAgentProperty]), UserAgentAsserter_UserAgentPropertyImplIe8_0);
_.getCompileTimeValue = function getCompileTimeValue_1(){
  return 'ie8';
}
;
_.getRuntimeValue = function getRuntimeValue_1(){
  var ua = navigator.userAgent.toLowerCase();
  var makeVersion = function(result){
    return parseInt(result[1]) * 1000 + parseInt(result[2]);
  }
  ;
  if (function(){
    return ua.indexOf('opera') != -1;
  }
  ())
    return 'opera';
  if (function(){
    return ua.indexOf('webkit') != -1;
  }
  ())
    return 'safari';
  if (function(){
    return ua.indexOf('msie') != -1 && $doc.documentMode >= 9;
  }
  ())
    return 'ie9';
  if (function(){
    return ua.indexOf('msie') != -1 && $doc.documentMode >= 8;
  }
  ())
    return 'ie8';
  if (function(){
    var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
    if (result && result.length == 3)
      return makeVersion(result) >= 6000;
  }
  ())
    return 'ie6';
  if (function(){
    return ua.indexOf('gecko') != -1;
  }
  ())
    return 'gecko1_8';
  return 'unknown';
}
;
_.getUserAgentRuntimeWarning = function getUserAgentRuntimeWarning_1(){
  return true;
}
;
function UserAgentAsserter_UserAgentPropertyImplIe9_0(){
}

defineSeed(48, 1, makeCastMap([Q$UserAgentAsserter$UserAgentProperty]), UserAgentAsserter_UserAgentPropertyImplIe9_0);
_.getCompileTimeValue = function getCompileTimeValue_2(){
  return 'ie9';
}
;
_.getRuntimeValue = function getRuntimeValue_2(){
  var ua = navigator.userAgent.toLowerCase();
  var makeVersion = function(result){
    return parseInt(result[1]) * 1000 + parseInt(result[2]);
  }
  ;
  if (function(){
    return ua.indexOf('opera') != -1;
  }
  ())
    return 'opera';
  if (function(){
    return ua.indexOf('webkit') != -1;
  }
  ())
    return 'safari';
  if (function(){
    return ua.indexOf('msie') != -1 && $doc.documentMode >= 9;
  }
  ())
    return 'ie9';
  if (function(){
    return ua.indexOf('msie') != -1 && $doc.documentMode >= 8;
  }
  ())
    return 'ie8';
  if (function(){
    var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
    if (result && result.length == 3)
      return makeVersion(result) >= 6000;
  }
  ())
    return 'ie6';
  if (function(){
    return ua.indexOf('gecko') != -1;
  }
  ())
    return 'gecko1_8';
  return 'unknown';
}
;
_.getUserAgentRuntimeWarning = function getUserAgentRuntimeWarning_2(){
  return true;
}
;
function UserAgentAsserter_UserAgentPropertyImplOpera_0(){
}

defineSeed(49, 1, makeCastMap([Q$UserAgentAsserter$UserAgentProperty]), UserAgentAsserter_UserAgentPropertyImplOpera_0);
_.getCompileTimeValue = function getCompileTimeValue_3(){
  return 'opera';
}
;
_.getRuntimeValue = function getRuntimeValue_3(){
  var ua = navigator.userAgent.toLowerCase();
  var makeVersion = function(result){
    return parseInt(result[1]) * 1000 + parseInt(result[2]);
  }
  ;
  if (function(){
    return ua.indexOf('opera') != -1;
  }
  ())
    return 'opera';
  if (function(){
    return ua.indexOf('webkit') != -1;
  }
  ())
    return 'safari';
  if (function(){
    return ua.indexOf('msie') != -1 && $doc.documentMode >= 9;
  }
  ())
    return 'ie9';
  if (function(){
    return ua.indexOf('msie') != -1 && $doc.documentMode >= 8;
  }
  ())
    return 'ie8';
  if (function(){
    var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
    if (result && result.length == 3)
      return makeVersion(result) >= 6000;
  }
  ())
    return 'ie6';
  if (function(){
    return ua.indexOf('gecko') != -1;
  }
  ())
    return 'gecko1_8';
  return 'unknown';
}
;
_.getUserAgentRuntimeWarning = function getUserAgentRuntimeWarning_3(){
  return true;
}
;
function UserAgentAsserter_UserAgentPropertyImplSafari_0(){
}

defineSeed(50, 1, makeCastMap([Q$UserAgentAsserter$UserAgentProperty]), UserAgentAsserter_UserAgentPropertyImplSafari_0);
_.getCompileTimeValue = function getCompileTimeValue_4(){
  return 'safari';
}
;
_.getRuntimeValue = function getRuntimeValue_4(){
  var ua = navigator.userAgent.toLowerCase();
  var makeVersion = function(result){
    return parseInt(result[1]) * 1000 + parseInt(result[2]);
  }
  ;
  if (function(){
    return ua.indexOf('opera') != -1;
  }
  ())
    return 'opera';
  if (function(){
    return ua.indexOf('webkit') != -1;
  }
  ())
    return 'safari';
  if (function(){
    return ua.indexOf('msie') != -1 && $doc.documentMode >= 9;
  }
  ())
    return 'ie9';
  if (function(){
    return ua.indexOf('msie') != -1 && $doc.documentMode >= 8;
  }
  ())
    return 'ie8';
  if (function(){
    var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
    if (result && result.length == 3)
      return makeVersion(result) >= 6000;
  }
  ())
    return 'ie6';
  if (function(){
    return ua.indexOf('gecko') != -1;
  }
  ())
    return 'gecko1_8';
  return 'unknown';
}
;
_.getUserAgentRuntimeWarning = function getUserAgentRuntimeWarning_4(){
  return true;
}
;
function $getBaseDirectory(){
  var pos, url;
  url = $wnd.location.href;
  pos = $lastIndexOf(url, fromCodePoint(47));
  if (pos < 0)
    throw new DictionaryException_0('URL could not be parsed');
  return url.substr(0, pos - 0);
}

function $onModuleLoad_1(this$static){
  var e, utilObj;
  utilObj = new UtilJs_1;
  utilObj_0 = utilObj;
  $exportStaticClasses(utilObj);
  contentParserObj = new ContentParser_1;
  exportStaticClasses();
  try {
    setBaseDirectory($getBaseDirectory());
    $clinit_CsvFile();
    fileStorageReader = new HTRFileStorageReader_0;
    this$static.dictionary_0 = ($clinit_TranslationExecution() , new DictionaryDataFile_0(new HTRInputStreamAccess_0));
    $setTranslationExecutionCallback(accessToHMIObj, this$static);
    exportLoadedDictionary(this$static.dictionary_0);
  }
   catch ($e0) {
    $e0 = caught($e0);
    if (instanceOf($e0, Q$Exception)) {
      e = $e0;
      $outputMessage('Thrown ' + $toString(e) + ' / ' + e.getMessage() + '\n');
      $printStackTrace(e);
    }
     else 
      throw $e0;
  }
}

function TranslationLayerGWT_0(){
}

function callNewTranslationResultJs(resultOfTranslation){
  resultOfTranslation.numberOfFoundTranslations = $entry(resultOfTranslation.numberOfFoundTranslations_0);
  resultOfTranslation.translationFound = $entry(resultOfTranslation.translationFound_0);
  resultOfTranslation.getTranslationAt = $entry(resultOfTranslation.getTranslationAt_0);
  for (var i = 0; i < resultOfTranslation.numberOfFoundTranslations(); i++) {
    singleTranslation = resultOfTranslation.getTranslationAt(i);
    singleTranslation.getFromText = $entry(singleTranslation.getFromText_0);
    singleTranslation.getNumberOfToTexts = $entry(singleTranslation.getNumberOfToTexts_0);
    singleTranslation.getToTextAt = $entry(singleTranslation.getToTextAt_0);
    setJsMethodsForJavaTextOfLanguage(singleTranslation.getFromText);
    for (var j = 0; j < singleTranslation.getNumberOfToTexts(); j++) {
      setJsMethodsForJavaTextOfLanguage(singleTranslation.getToTextAt(j));
    }
  }
  function setJsMethodsForJavaTextOfLanguage(textOfLanguage){
    textOfLanguage.getLanguageIndex = $entry(textOfLanguage.getLanguageIndex_0);
    textOfLanguage.getText = $entry(textOfLanguage.getText_0);
  }

  TranslationExecution.newTranslationResultCallback(resultOfTranslation);
}

function executeTranslation(translationParametersObj_0){
  var translationParametersBatchObj;
  $clinit_TranslationExecution();
  translationParametersBatchObj = new TranslationParametersBatch_0;
  $addElement(translationParametersBatchObj.translationParametersVector, translationParametersObj_0);
  executeTranslationBatch_0(translationParametersBatchObj);
}

function executeTranslationBatch(translationParametersBatchObj){
  executeTranslationBatch_0(translationParametersBatchObj);
}

function exportLoadedDictionary(dictionary){
  function setJsFieldsForLanguageDefinition(supportedLanguageObj){
    supportedLanguageObj.languageDisplayText = supportedLanguageObj.languageDisplayText_0;
    supportedLanguageObj.languageFilePostfix = supportedLanguageObj.languageFilePostfix_0;
    supportedLanguageObj.normationClassName = supportedLanguageObj.normationClassName_0;
    supportedLanguageObj.isSearchable = supportedLanguageObj.isSearchable_0;
    supportedLanguageObj.normationObj = supportedLanguageObj.normationObj_0;
    supportedLanguageObj.indexNumberOfSourceEntries = supportedLanguageObj.indexNumberOfSourceEntries_0;
    supportedLanguageObj.contentDefinitionAvailable = supportedLanguageObj.contentDefinitionAvailable_0;
    supportedLanguageObj.languageIcon = supportedLanguageObj.languageIcon_0;
    supportedLanguageObj.contents = supportedLanguageObj.contents_0;
    for (var i = 0; i < supportedLanguageObj.contents.length; ++i) {
      setJsFieldsForContentDefinition(supportedLanguageObj.contents[i]);
    }
  }

  function setJsFieldsForContentDefinition(contentObj){
    contentObj.contentDisplayText = contentObj.contentDisplayText_0;
    contentObj.fontColour = contentObj.fontColour_0;
    contentObj.fontStyle = contentObj.fontStyle_0;
    contentObj.selectionMode = contentObj.selectionMode_0;
    contentObj.displaySelectable = contentObj.displaySelectable_0;
  }

  dictionary.numberOfAvailableLanguages = dictionary.numberOfAvailableLanguages_0;
  dictionary.numberOfInputLanguages = dictionary.numberOfInputLanguages_0;
  dictionary.infoText = dictionary.infoText_0;
  dictionary.dictionaryAbbreviation = dictionary.dictionaryAbbreviation_0;
  dictionary.applicationFileNamePrefix = applicationFileNamePrefix;
  dictionary.supportedLanguages = dictionary.supportedLanguages_0;
  var supportedLanguages = dictionary.supportedLanguages;
  for (var i_0 = 0; i_0 < dictionary.numberOfAvailableLanguages; ++i_0) {
    setJsFieldsForLanguageDefinition(supportedLanguages[i_0]);
  }
  $wnd.dictionary = dictionary;
}

function exportStaticClasses(){
  function newTranslationParametersJs(dictionary, toBeTranslatedWordTextInputParam, inputLanguagesParam, outputLanguagesParam, executeInBackgroundParam, maxHitsParam, durationForCancelSearchParam){
    var newTranslationParametersFunction = $entry(newTranslationParameters);
    translationParametersObj = newTranslationParametersFunction(dictionary, toBeTranslatedWordTextInputParam, inputLanguagesParam, outputLanguagesParam, executeInBackgroundParam, maxHitsParam, durationForCancelSearchParam);
    translationParametersObj.getInputLanguages = $entry(translationParametersObj.getInputLanguages_0);
    translationParametersObj.getOutputLanguages = $entry(translationParametersObj.getOutputLanguages_0);
    translationParametersObj.getDictionary = $entry(translationParametersObj.getDictionary_0);
    translationParametersObj.getToBeTranslatedWordText = $entry(translationParametersObj.getToBeTranslatedWordText_0);
    translationParametersObj.getDurationForCancelSearch = $entry(translationParametersObj.getDurationForCancelSearch_0);
    translationParametersObj.getMaxHits = $entry(translationParametersObj.getMaxHits_0);
    return translationParametersObj;
  }

  function setTranslationResultCallbackJs(deletePreviousTranslationResultCallbackParam, newTranslationResultCallbackParam){
    TranslationExecution_0.deletePreviousTranslationResultCallback = deletePreviousTranslationResultCallbackParam;
    TranslationExecution_0.newTranslationResultCallback = newTranslationResultCallbackParam;
  }

  function executeTranslationJs(translationParametersObj_0, deletePreviousTranslationResultCallback, newTranslationResultCallback){
    setTranslationResultCallbackJs(deletePreviousTranslationResultCallback, newTranslationResultCallback);
    var executeTranslationFunction = $entry(executeTranslation);
    executeTranslationFunction(translationParametersObj_0);
  }

  function executeTranslationBatchJs(translationParametersBatchObj, deletePreviousTranslationResultCallback, newTranslationResultCallback){
    setTranslationResultCallbackJs(deletePreviousTranslationResultCallback, newTranslationResultCallback);
    var executeTranslationBatchFunction = $entry(executeTranslationBatch);
    executeTranslationBatchFunction(translationParametersBatchObj);
  }

  $wnd.TranslationExecution = new Object;
  var TranslationExecution_0 = $wnd.TranslationExecution;
  TranslationExecution_0.executeTranslation = executeTranslationJs;
  TranslationExecution_0.executeTranslationBatch = executeTranslationBatchJs;
  TranslationExecution_0.newTranslationParameters = newTranslationParametersJs;
  function determineItemsFromContent(contentText, changeInputAndOutputContent, isInput){
    var stringColourItemText = $determineItemsFromContent(contentParserObj, contentText, changeInputAndOutputContent, isInput);
    stringColourItemText.getItemTextPart = $entry(stringColourItemText.getItemTextPart_0);
    stringColourItemText.size = $entry(stringColourItemText.size_1);
    for (var i = 0; i < stringColourItemText.size(); i++) {
      stringColourItemTextPart = stringColourItemText.getItemTextPart(i);
      stringColourItemTextPart.getText = $entry(stringColourItemTextPart.getText_0);
      stringColourItemTextPart.getColour = $entry(stringColourItemTextPart.getColour_0);
      rgbColour = stringColourItemTextPart.getColour();
      rgbColour.getHexValue = $entry(rgbColour.getHexValue_0);
      stringColourItemTextPart.getStyle = $entry(stringColourItemTextPart.getStyle_0);
      style = stringColourItemTextPart.getStyle();
      style.style = style.style_0;
    }
    return stringColourItemText;
  }

  $wnd.ContentParser = new Object;
  var ContentParser = $wnd.ContentParser;
  ContentParser.determineItemsFromContent = determineItemsFromContent;
  $wnd.PredefinedContent = new Object;
  var predefinedContent = $wnd.PredefinedContent;
  predefinedContent.getPredefinedContent = $entry(getPredefinedContent);
}

function newTranslationParameters(dictionary, toBeTranslatedWordTextInputParam, inputLanguagesParam, outputLanguagesParam, executeInBackgroundParam, maxHitsParam, durationForCancelSearchParam){
  var i, inputLanguages, outputLanguages, translationParametersObj_0;
  inputLanguages = initDim(_3Z_classLit, makeCastMap([Q$Serializable]), -1, inputLanguagesParam.length, 2);
  outputLanguages = initDim(_3Z_classLit, makeCastMap([Q$Serializable]), -1, outputLanguagesParam.length, 2);
  for (i = 0; i < inputLanguagesParam.length; ++i) {
    inputLanguages[i] = inputLanguagesParam[i];
  }
  for (i = 0; i < outputLanguagesParam.length; ++i) {
    outputLanguages[i] = outputLanguagesParam[i];
  }
  translationParametersObj_0 = new TranslationParameters_0(dictionary, toBeTranslatedWordTextInputParam, inputLanguages, outputLanguages, executeInBackgroundParam, maxHitsParam, durationForCancelSearchParam);
  return translationParametersObj_0;
}

defineSeed(51, 1, {}, TranslationLayerGWT_0);
_.dictionary_0 = null;
var contentParserObj = null;
function $clinit_CsvFile(){
  $clinit_CsvFile = nullMethod;
  fileStorageReader = new DefaultFileStorageReader_0;
}

function $getRestOfLine(this$static){
  var charactersInFile, currentCharacter, endOfLineFound, line;
  charactersInFile = this$static.fileStorageObj.charactersInFile;
  line = new StringBuffer_0;
  endOfLineFound = false;
  $setLength(line, 0);
  if (this$static.position < charactersInFile) {
    do {
      currentCharacter = $readCharacterAt(this$static.fileStorageObj, this$static.position);
      if (currentCharacter == 10) {
        this$static.columnNumber = 0;
        endOfLineFound = true;
        ++this$static.position;
      }
       else {
        currentCharacter != 13 && (line.impl.appendNonNull(line.data, String.fromCharCode(currentCharacter)) , line);
        ++this$static.position;
      }
      if (this$static.position == charactersInFile) {
        endOfLineFound = true;
        this$static.endOfDictionaryReached = true;
      }
    }
     while (!endOfLineFound);
  }
  return line;
}

function $getWord(this$static){
  var charactersInFile, currentCharacter, endOfWordFound, word;
  charactersInFile = this$static.fileStorageObj.charactersInFile;
  word = new StringBuffer_0;
  endOfWordFound = false;
  $setLength(word, 0);
  if (this$static.position < charactersInFile) {
    do {
      currentCharacter = $readCharacterAt(this$static.fileStorageObj, this$static.position);
      if (currentCharacter == 10) {
        this$static.columnNumber = 0;
        endOfWordFound = true;
        ++this$static.position;
      }
       else if (currentCharacter == this$static.separatorCharacter) {
        ++this$static.columnNumber;
        endOfWordFound = true;
        ++this$static.position;
      }
       else {
        currentCharacter != 13 && (word.impl.appendNonNull(word.data, String.fromCharCode(currentCharacter)) , word);
        ++this$static.position;
      }
      if (this$static.position == charactersInFile) {
        endOfWordFound = true;
        this$static.endOfDictionaryReached = true;
      }
    }
     while (!endOfWordFound);
  }
  return word;
}

function $setParams(this$static, dictionaryDataFileISAccessParam, fileNameParam, separatorCharacterParam, charEncodingParam, maxSizeOfFileDataParam){
  this$static.dictionaryDataFileISAccess = dictionaryDataFileISAccessParam;
  this$static.separatorCharacter = separatorCharacterParam;
  this$static.charEncoding = charEncodingParam;
  this$static.maxSizeOfFileData = maxSizeOfFileDataParam;
  this$static.fileName = fileNameParam;
}

function $setPositionBefore(this$static, searchCriteria){
  var charactersInFile, comparison, currentPosition, lastPositionAfter, lastPositionBefore, numberOfLoops, word;
  charactersInFile = this$static.fileStorageObj.charactersInFile;
  lastPositionBefore = 0;
  currentPosition = ~~(charactersInFile / 2);
  lastPositionAfter = charactersInFile - 1;
  numberOfLoops = 0;
  do {
    ++numberOfLoops;
    while (currentPosition < charactersInFile && $readCharacterAt(this$static.fileStorageObj, currentPosition) != 10) {
      ++currentPosition;
    }
    ++currentPosition;
    if (currentPosition >= charactersInFile) {
      break;
    }
    this$static.position = currentPosition;
    word = $getWord(this$static);
    comparison = compareTo(searchCriteria, word.impl.toString_0(word.data));
    if (comparison < 0) {
      if (currentPosition == lastPositionAfter) {
        break;
      }
       else {
        lastPositionAfter = currentPosition;
        currentPosition = currentPosition - ~~((currentPosition - lastPositionBefore) / 2);
      }
    }
     else if (comparison > 0) {
      if (currentPosition == lastPositionAfter) {
        break;
      }
       else {
        lastPositionBefore = currentPosition;
        currentPosition = currentPosition + ~~((lastPositionAfter - currentPosition) / 2);
      }
    }
     else if (comparison == 0) {
      lastPositionBefore = currentPosition;
      break;
    }
  }
   while (true);
  this$static.position = lastPositionBefore;
  $log_0(utilObj_0, 'pos before: ' + this$static.position, 3);
}

function $skipRestOfLine(this$static){
  var charactersInFile, currentCharacter, endOfLineFound;
  charactersInFile = this$static.fileStorageObj.charactersInFile;
  endOfLineFound = false;
  if (this$static.position < charactersInFile) {
    do {
      currentCharacter = $readCharacterAt(this$static.fileStorageObj, this$static.position);
      if (this$static.position == charactersInFile - 1) {
        endOfLineFound = true;
        this$static.endOfDictionaryReached = true;
      }
       else if (currentCharacter == 10) {
        this$static.columnNumber = 0;
        endOfLineFound = true;
        ++this$static.position;
      }
       else {
        ++this$static.position;
      }
    }
     while (!endOfLineFound);
  }
}

function CsvFile_0(dictionaryDataFileISAccessParam, fileNameParam, separatorCharacterParam, charEncodingParam, maxSizeOfFileDataParam){
  $clinit_CsvFile();
  $setParams(this, dictionaryDataFileISAccessParam, fileNameParam, separatorCharacterParam, charEncodingParam, maxSizeOfFileDataParam);
  this.fileStorageObj = fileStorageReader.readFileToFileStorage(this.dictionaryDataFileISAccess, this.fileName, this.charEncoding, this.maxSizeOfFileData);
}

function CsvFile_1(dictionaryDataFileISAccessParam, fileNameParam, separatorCharacterParam, charEncodingParam, maxSizeOfFileDataParam, startPositionParam){
  $clinit_CsvFile();
  $setParams(this, dictionaryDataFileISAccessParam, fileNameParam, separatorCharacterParam, charEncodingParam, maxSizeOfFileDataParam);
  this.fileStorageObj = fileStorageReader.readCsvFileLine(this.dictionaryDataFileISAccess, this.fileName, this.charEncoding, startPositionParam);
}

defineSeed(52, 1, {}, CsvFile_0, CsvFile_1);
_.charEncoding = null;
_.columnNumber = 0;
_.dictionaryDataFileISAccess = null;
_.endOfDictionaryReached = false;
_.fileName = null;
_.fileStorageObj = null;
_.maxSizeOfFileData = 0;
_.position = 0;
_.separatorCharacter = 0;
var fileStorageReader;
function $getCsvFile(this$static, fileNameParam, startPosition){
  var csvStream, skipResult, skippedBytes, startTime;
  startTime = fromDouble(currentTimeMillis0());
  this$static.fileName = fileNameParam;
  csvStream = new HTRInputStream_0(this$static.fileName);
  $logTime(utilObj_0, 'open file', startTime);
  startTime = fromDouble(currentTimeMillis0());
  if (csvStream) {
    skippedBytes = P0_longLit;
    while (neq(skippedBytes, fromInt(startPosition))) {
      skipResult = $skip(csvStream, sub_0(fromInt(startPosition), skippedBytes));
      if (gt(skipResult, P0_longLit)) {
        skippedBytes = add(skippedBytes, skipResult);
      }
       else {
        break;
      }
    }
    $logTime(utilObj_0, 'position file', startTime);
    if (neq(skippedBytes, fromInt(startPosition))) {
      throw new DictionaryException_0('CSV file: skipped only ' + toString_4(skippedBytes) + ' bytes');
    }
  }
   else {
    throw new DictionaryException_0('Could not open file ' + this$static.fileName);
  }
  return csvStream;
}

function CsvFileCache_0(){
}

defineSeed(53, 1, {}, CsvFileCache_0);
_.fileName = null;
function $clinit_DefaultFileStorageReader(){
  $clinit_DefaultFileStorageReader = nullMethod;
  fileCache = new CsvFileCache_0;
}

function $readCsvFileLine(fileName, charEncoding, startPosition){
  var character, csvLineString, csvStream, csvStreamReader, e, endOfLineReached, fileStorageObj, sizeOfFile, startTime;
  try {
    csvStream = $getCsvFile(fileCache, fileName, startPosition);
    startTime = fromDouble(currentTimeMillis0());
    sizeOfFile = 0;
    csvLineString = new StringBuffer_0;
    csvStreamReader = new InputStreamReader_0(csvStream, charEncoding);
    endOfLineReached = false;
    do {
      character = $read_1(csvStreamReader);
      if (character != 10 && character != -1) {
        $append(csvLineString, character & 65535);
        ++sizeOfFile;
      }
       else {
        endOfLineReached = true;
      }
    }
     while (!endOfLineReached);
    $logTime(utilObj_0, 'read/parse file-line', startTime);
    fileStorageObj = new StringFileStorage_1(csvLineString);
  }
   catch ($e0) {
    $e0 = caught($e0);
    if (instanceOf($e0, Q$IOException)) {
      e = $e0;
      throw new DictionaryException_1(e);
    }
     else 
      throw $e0;
  }
  return fileStorageObj;
}

function DefaultFileStorageReader_0(){
  $clinit_DefaultFileStorageReader();
}

defineSeed(54, 1, {}, DefaultFileStorageReader_0);
_.readCsvFileLine = function readCsvFileLine(dictionaryDataFileISAccess, fileName, charEncoding, startPosition){
  return $readCsvFileLine(fileName, charEncoding, startPosition);
}
;
_.readFileToFileStorage = function readFileToFileStorage(dictionaryDataFileISAccess, fileName, charEncoding, maxSizeOfFileData){
  var bytesRead, character, csvStream, e, fileData, fileStorageObj, sizeOfFile, startTime;
  fileData = initDim(_3B_classLit, makeCastMap([Q$Serializable]), -1, maxSizeOfFileData, 1);
  try {
    startTime = fromDouble(currentTimeMillis0());
    csvStream = new HTRInputStream_0(fileName);
    $logTime(utilObj_0, 'open file', startTime);
    startTime = fromDouble(currentTimeMillis0());
    if (csvStream) {
      sizeOfFile = 0;
      do {
        bytesRead = $read(csvStream, fileData, sizeOfFile, fileData.length - sizeOfFile);
        bytesRead != -1 && (sizeOfFile += bytesRead);
        if (sizeOfFile == fileData.length) {
          character = $read_0(csvStream);
          character != -1 && $log(utilObj_0, 'Warning: buffer size too small for file ' + fileName);
          break;
        }
      }
       while (bytesRead != -1);
      $logTime(utilObj_0, 'read file', startTime);
      startTime = fromDouble(currentTimeMillis0());
      fileStorageObj = new StringFileStorage_2(fileData, sizeOfFile, charEncoding);
      $logTime(utilObj_0, 'parse file', startTime);
    }
     else {
      throw new DictionaryException_0('Could not open file ' + fileName);
    }
  }
   catch ($e0) {
    $e0 = caught($e0);
    if (instanceOf($e0, Q$IOException)) {
      e = $e0;
      throw new DictionaryException_1(e);
    }
     else 
      throw $e0;
  }
  return fileStorageObj;
}
;
var fileCache;
function $determineColourComponent(rbgString, propertyName){
  var e, rbgStringTrimmed, rgbValue;
  rbgStringTrimmed = $trim(rbgString);
  rgbValue = 0;
  try {
    rgbValue = __parseAndValidateInt(rbgStringTrimmed, 10);
  }
   catch ($e0) {
    $e0 = caught($e0);
    if (instanceOf($e0, Q$NumberFormatException)) {
      e = $e0;
      $throwContentException('RGB value is incorrect: ' + e.detailMessage, propertyName);
    }
     else 
      throw $e0;
  }
  rgbValue > 255 && $throwContentException('RGB value is bigger than 255', propertyName);
  return rgbValue;
}

function $determineRGBColourFromProperty(fontColourString, propertyName){
  var blue, fontColourStringElements, green, red;
  fontColourStringElements = stringSplit(fontColourString);
  fontColourStringElements.length != 3 && $throwContentException('3 components reqired for font colour (red, green, blue)', propertyName);
  red = $determineColourComponent(fontColourStringElements[0], propertyName);
  green = $determineColourComponent(fontColourStringElements[1], propertyName);
  blue = $determineColourComponent(fontColourStringElements[2], propertyName);
  return new RGBColour_0(red, green, blue);
}

function $getObjectForClass(this$static, className, fallbackClassName){
  var classNameNewPackage, classNameNewPackageStr, classObj;
  className == null && (className = fallbackClassName);
  classObj = $createObjectForClass(this$static.classMethodObj, className);
  if (classObj == null) {
    classNameNewPackage = new StringBuffer_2(className);
    classNameNewPackage.impl.replace_0(classNameNewPackage.data, 0, 40, '');
    classNameNewPackageStr = 'de.kugihan.dictionaryformids.translation.normation' + classNameNewPackage.impl.toString_0(classNameNewPackage.data);
    classObj = $createObjectForClass(this$static.classMethodObj, classNameNewPackageStr);
    if (classObj == null) {
      throw new DictionaryClassNotLoadedException_0('Class could not be loaded: ' + className);
    }
  }
  return classObj;
}

function $throwContentException(message, propertyName){
  throw new DictionaryException_0(propertyName + ': ' + message);
}

function DictionaryDataFile_0(dictionaryDataFileISAccessParam){
  var backgroundColourString, content_0, contentDefinitionAvailable, contentDisplayText, contentDisplayTextProperty, contentFromPropertyFile, contentPropertyPrefix, displaySelectable, displaySelectablePropertyName, displaySelectableString, fontColour, fontColourString, fontStyle, fontStyleString, hasSeparateDictionaryFile, indexContent, indexContentString, indexLanguage, indexLanguageString, indexNumberOfSourceEntries, isSearchable, languageDisplayText, languageFilePostfix, languagePropertyPrefix, normationClassName, normationObj, numberOfContentDeclarations, numberOfContentDeclarationsString, selectionMode, selectionModeString, utilObj, propertyValue;
  this.classMethodObj = new ClassMethodImpl_0;
  this.dictionaryDataFileISAccess = dictionaryDataFileISAccessParam;
  utilObj = utilObj_0;
  $openProperties(utilObj);
  this.infoText_0 = $getDictionaryPropertyString(utilObj, 'infoText', false);
  this.dictionaryAbbreviation_0 = $getDictionaryPropertyString(utilObj, 'dictionaryAbbreviation', true);
  this.numberOfAvailableLanguages_0 = (propertyValue = $getDictionaryPropertyString(utilObj, 'numberOfAvailableLanguages', false) , valueOf(__parseAndValidateInt(propertyValue, 10)).value);
  this.numberOfInputLanguages_0 = 0;
  this.supportedLanguages_0 = initDim(_3Lde_kugihan_dictionaryformids_dataaccess_LanguageDefinition_2_classLit, makeCastMap([Q$Serializable]), Q$LanguageDefinition, this.numberOfAvailableLanguages_0, 0);
  for (indexLanguage = 0; indexLanguage < this.numberOfAvailableLanguages_0; ++indexLanguage) {
    indexLanguageString = '' + (indexLanguage + 1);
    languagePropertyPrefix = 'language' + indexLanguageString;
    languageDisplayText = $getDictionaryPropertyString(utilObj, languagePropertyPrefix + 'DisplayText', false);
    languageFilePostfix = $getDictionaryPropertyString(utilObj, languagePropertyPrefix + 'FilePostfix', false);
    isSearchable = $getDictionaryPropertyBooleanDefault(utilObj, languagePropertyPrefix + 'IsSearchable', true);
    isSearchable && ++this.numberOfInputLanguages_0;
    hasSeparateDictionaryFile = $getDictionaryPropertyBooleanDefault(utilObj, languagePropertyPrefix + 'HasSeparateDictionaryFile', false);
    $getDictionaryPropertyBooleanDefault(utilObj, languagePropertyPrefix + 'GenerateIndex', true);
    normationClassName = $getDictionaryPropertyString(utilObj, languagePropertyPrefix + 'NormationClassName', true);
    $getDictionaryPropertyString(utilObj, languagePropertyPrefix + 'DictionaryUpdateClassName', true);
    indexNumberOfSourceEntries = $getDictionaryPropertyIntDefault(utilObj, languagePropertyPrefix + 'IndexNumberOfSourceEntries', -1);
    contentDefinitionAvailable = false;
    numberOfContentDeclarations = 1;
    numberOfContentDeclarationsString = $getDictionaryPropertyString(utilObj, languagePropertyPrefix + 'NumberOfContentDeclarations', true);
    if (numberOfContentDeclarationsString != null) {
      numberOfContentDeclarations = valueOf(__parseAndValidateInt(numberOfContentDeclarationsString, 10)).value + 1;
      contentDefinitionAvailable = true;
    }
    content_0 = initDim(_3Lde_kugihan_dictionaryformids_dataaccess_content_ContentDefinition_2_classLit, makeCastMap([Q$Serializable]), Q$ContentDefinition, numberOfContentDeclarations, 0);
    content_0[0] = ($clinit_PredefinedContent() , $clinit_PredefinedContent() , !predefinedContentMap && initPredefinedContent() , $clinit_PredefinedContent() , contentNoDefinitionProvided);
    for (indexContent = 0; indexContent < numberOfContentDeclarations; ++indexContent) {
      indexContentString = '' + indexContent;
      if (indexContentString.length == 1)
        indexContentString = '0' + indexContentString;
      else if (indexContentString.length > 2)
        throw new DictionaryException_0('Number of contents too big');
      contentPropertyPrefix = languagePropertyPrefix + 'Content' + indexContentString;
      contentDisplayTextProperty = contentPropertyPrefix + 'DisplayText';
      contentDisplayText = $getDictionaryPropertyString(utilObj, contentDisplayTextProperty, true);
      if (contentDisplayText == null) {
        if (indexContent == 0) {
          continue;
        }
         else {
          $propertyNotFound(contentDisplayTextProperty);
        }
      }
      contentDisplayText.indexOf('content') == 0?(contentFromPropertyFile = getPredefinedContent(contentDisplayText)):(contentFromPropertyFile = new ContentDefinition_0(contentDisplayText));
      fontColourString = $getDictionaryPropertyString(utilObj, contentPropertyPrefix + 'FontColour', true);
      if (fontColourString != null) {
        fontColour = $determineRGBColourFromProperty(fontColourString, contentPropertyPrefix);
        contentFromPropertyFile.fontColour_0 = fontColour;
      }
      fontStyleString = $getDictionaryPropertyString(utilObj, contentPropertyPrefix + 'FontStyle', true);
      if (fontStyleString != null) {
        fontStyle = null;
        stringEqualIgnoreCase(fontStyleString, 'plain')?(fontStyle = fontStylePlain):stringEqualIgnoreCase(fontStyleString, 'underlined')?(fontStyle = fontStyleUnderlined):stringEqualIgnoreCase(fontStyleString, 'bold')?(fontStyle = fontStyleBold):stringEqualIgnoreCase(fontStyleString, 'italic')?(fontStyle = fontStyleItalic):$throwContentException('Incorrect font style', contentPropertyPrefix);
        contentFromPropertyFile.fontStyle_0 = fontStyle;
      }
      displaySelectablePropertyName = contentPropertyPrefix + 'DisplaySelectable';
      displaySelectableString = $getDictionaryPropertyString(utilObj, displaySelectablePropertyName, true);
      if (displaySelectableString != null) {
        displaySelectable = $getBooleanFromProperty(utilObj, displaySelectablePropertyName, displaySelectableString);
        contentFromPropertyFile.displaySelectable_0 = displaySelectable;
      }
      selectionModeString = $getDictionaryPropertyString(utilObj, contentPropertyPrefix + 'SelectionMode', true);
      if (selectionModeString != null) {
        selectionMode = null;
        stringEqualIgnoreCase(selectionModeString, 'none')?(selectionMode = selectionModeNone):stringEqualIgnoreCase(selectionModeString, 'single')?(selectionMode = selectionModeSingle):stringEqualIgnoreCase(selectionModeString, 'all')?(selectionMode = selectionModeAll):$throwContentException('Incorrect selection mode', contentPropertyPrefix);
        contentFromPropertyFile.selectionMode_0 = selectionMode;
      }
      content_0[indexContent] = contentFromPropertyFile;
    }
    this.supportedLanguages_0[indexLanguage] = new LanguageDefinition_0(languageDisplayText, languageFilePostfix, isSearchable, hasSeparateDictionaryFile, normationClassName, indexNumberOfSourceEntries, contentDefinitionAvailable, content_0);
  }
  this.searchListCharEncoding = $getDictionaryPropertyString(utilObj, 'searchListCharEncoding', false);
  this.searchListCharEncoding = this.searchListCharEncoding;
  this.searchListFileSeparationCharacter = $getDictionaryPropertyChar(utilObj, 'searchListFileSeparationCharacter');
  this.searchListFileMaxSize = $getDictionaryPropertyIntDefault(utilObj, 'searchListFileMaxSize', 10000);
  this.indexCharEncoding = $getDictionaryPropertyString(utilObj, 'indexCharEncoding', false);
  this.indexCharEncoding = this.indexCharEncoding;
  this.indexFileSeparationCharacter = $getDictionaryPropertyChar(utilObj, 'indexFileSeparationCharacter');
  this.indexFileMaxSize = $getDictionaryPropertyIntDefault(utilObj, 'indexFileMaxSize', 10000);
  this.dictionaryCharEncoding = $getDictionaryPropertyString(utilObj, 'dictionaryCharEncoding', false);
  this.dictionaryCharEncoding = this.dictionaryCharEncoding;
  this.dictionaryFileSeparationCharacter = $getDictionaryPropertyChar(utilObj, 'dictionaryFileSeparationCharacter');
  this.dictionaryFileMaxSize = $getDictionaryPropertyIntDefault(utilObj, 'dictionaryFileMaxSize', 10000);
  backgroundColourString = $getDictionaryPropertyString(utilObj, 'backgroundColour', true);
  if (backgroundColourString == null)
  ;
  else {
    stringEqualIgnoreCase(backgroundColourString, 'backgroundColourDefault')?($clinit_PredefinedContent() , undefined):($determineRGBColourFromProperty(backgroundColourString, 'backgroundColour') , undefined);
  }
  this.fileEncodingFormat = $getDictionaryPropertyString(utilObj, 'fileEncodingFormat', true);
  this.fileEncodingFormat == null && (this.fileEncodingFormat = 'plain_format1');
  for (indexLanguage = 0; indexLanguage < this.numberOfAvailableLanguages_0; ++indexLanguage) {
    normationClassName = this.supportedLanguages_0[indexLanguage].normationClassName_0;
    normationObj = dynamicCast($getObjectForClass(this, normationClassName, Lde_kugihan_dictionaryformids_translation_normation_Normation_2_classLit.typeName), Q$Normation);
    normationObj.dictionaryDataFileISAccess = this.dictionaryDataFileISAccess;
    this.supportedLanguages_0[indexLanguage].normationObj_0 = normationObj;
  }
}

defineSeed(55, 1, {}, DictionaryDataFile_0);
_.dictionaryAbbreviation_0 = null;
_.dictionaryCharEncoding = null;
_.dictionaryDataFileISAccess = null;
_.dictionaryFileMaxSize = 0;
_.dictionaryFileSeparationCharacter = 0;
_.fileEncodingFormat = null;
_.indexCharEncoding = null;
_.indexFileMaxSize = 0;
_.indexFileSeparationCharacter = 0;
_.infoText_0 = null;
_.numberOfAvailableLanguages_0 = 0;
_.numberOfInputLanguages_0 = 0;
_.searchListCharEncoding = null;
_.searchListFileMaxSize = 0;
_.searchListFileSeparationCharacter = 0;
_.supportedLanguages_0 = null;
var applicationFileNamePrefix = 'DictionaryForMIDs';
function GetISO88591Character_0(){
}

defineSeed(56, 1, {}, GetISO88591Character_0);
_.getCharacter = function getCharacter(inputStream){
  var charFromInputStream;
  charFromInputStream = $read_0(inputStream);
  return charFromInputStream;
}
;
function GetUTF8Character_0(){
}

defineSeed(57, 1, {}, GetUTF8Character_0);
_.getCharacter = function getCharacter_0(inputStream){
  var b, ch, count, extCh;
  count = 0;
  ch = $read_0(inputStream);
  if (ch != -1) {
    if ((ch & 128) == 0) {
      count = 1;
      ch &= 127;
    }
     else if ((ch & 224) == 192) {
      count = 2;
      ch &= 31;
    }
     else if ((ch & 240) == 224) {
      count = 3;
      ch &= 15;
    }
     else if ((ch & 248) == 240) {
      count = 4;
      ch &= 7;
    }
     else if ((ch & 252) == 248) {
      count = 5;
      ch &= 3;
    }
    while (--count > 0) {
      extCh = $read_0(inputStream);
      if (ch == -1) {
        throw new IllegalArgumentException_1('Invalid UTF8 sequence (EOF reached)');
      }
      b = ~~(extCh << 24) >> 24;
      if ((b & 192) != 128) {
        throw new IllegalArgumentException_1('Invalid UTF8 sequence ');
      }
      ch = ch << 6 | b & 63;
    }
  }
  return ch;
}
;
function $readHTRFileLine(fileName, startPosition, getCharacterObj){
  var charFromHTRStream, e, endOfLineReached, fileStorageObj, htrInputStream, htrLineStringBuffer;
  htrLineStringBuffer = new StringBuffer_0;
  try {
    htrInputStream = new HTRInputStream_0(fileName);
    $skip(htrInputStream, fromInt(startPosition));
    endOfLineReached = false;
    do {
      charFromHTRStream = getCharacterObj.getCharacter(htrInputStream);
      charFromHTRStream != 10 && charFromHTRStream != -1?$append(htrLineStringBuffer, charFromHTRStream & 65535):(endOfLineReached = true);
    }
     while (!endOfLineReached);
  }
   catch ($e0) {
    $e0 = caught($e0);
    if (instanceOf($e0, Q$IOException)) {
      e = $e0;
      throw new DictionaryException_1(e);
    }
     else 
      throw $e0;
  }
  fileStorageObj = new StringFileStorage_1(htrLineStringBuffer);
  return fileStorageObj;
}

function HTRFileStorageReader_0(){
  $clinit_DefaultFileStorageReader();
  this.getUTF8CharacterObj = new GetUTF8Character_0;
  this.getISO88591CharacterObj = new GetISO88591Character_0;
}

defineSeed(58, 54, {}, HTRFileStorageReader_0);
_.readCsvFileLine = function readCsvFileLine_0(dictionaryDataFileISAccess, fileName, charEncoding, startPosition){
  var fileStorageObj;
  $equalsIgnoreCase(charEncoding, 'UTF-8')?(fileStorageObj = $readHTRFileLine(fileName, startPosition, this.getUTF8CharacterObj)):$equalsIgnoreCase(charEncoding, 'ISO-8859-1')?(fileStorageObj = $readHTRFileLine(fileName, startPosition, this.getISO88591CharacterObj)):(fileStorageObj = $readCsvFileLine(fileName, charEncoding, startPosition));
  return fileStorageObj;
}
;
_.readFileToFileStorage = function readFileToFileStorage_0(dictionaryDataFileISAccess, fileName, charEncoding, maxSizeOfFileData){
  var fileDataString, fileStorageObj;
  fileDataString = readFile(fileName, charEncoding);
  fileStorageObj = new StringFileStorage_0(fileDataString);
  return fileStorageObj;
}
;
function LanguageDefinition_0(languageDisplayTextParam, languageFilePostfixParam, isSearchableParam, hasSeparateDictionaryFileParam, normationClassNameParam, indexNumberOfSourceEntriesParam, contentDefinitionAvailableParam, contentsParam){
  this.languageDisplayText_0 = languageDisplayTextParam;
  this.languageFilePostfix_0 = languageFilePostfixParam;
  this.isSearchable_0 = isSearchableParam;
  this.hasSeparateDictionaryFile = hasSeparateDictionaryFileParam;
  this.normationClassName_0 = normationClassNameParam;
  this.contentDefinitionAvailable_0 = contentDefinitionAvailableParam;
  this.contents_0 = contentsParam;
  this.indexNumberOfSourceEntries_0 = indexNumberOfSourceEntriesParam;
  this.languageIcon_0 = null;
}

defineSeed(59, 1, makeCastMap([Q$LanguageDefinition]), LanguageDefinition_0);
_.contentDefinitionAvailable_0 = false;
_.contents_0 = null;
_.hasSeparateDictionaryFile = false;
_.indexNumberOfSourceEntries_0 = 0;
_.isSearchable_0 = false;
_.languageDisplayText_0 = null;
_.languageFilePostfix_0 = null;
_.languageIcon_0 = null;
_.normationClassName_0 = null;
_.normationObj_0 = null;
function $readCharacterAt(this$static, pos){
  return $charAt(this$static.stringStorage, pos);
}

function StringFileStorage_0(content_0){
  this.stringStorage = content_0;
  this.charactersInFile = this.stringStorage.length;
}

function StringFileStorage_1(content_0){
  this.stringStorage = content_0.impl.toString_0(content_0.data);
  this.charactersInFile = this.stringStorage.length;
}

function StringFileStorage_2(content_0, sizeOfFile, charEncoding){
  this.stringStorage = _String(content_0, sizeOfFile, charEncoding);
  this.charactersInFile = this.stringStorage.length;
}

defineSeed(60, 1, {}, StringFileStorage_0, StringFileStorage_1, StringFileStorage_2);
_.charactersInFile = 0;
_.stringStorage = null;
function $setFontColour(this$static, fontColour){
  this$static.fontColour_0 = fontColour;
}

function $setFontStyle(this$static, fontStyle){
  this$static.fontStyle_0 = fontStyle;
}

function $setSelectionMode(this$static, selectionMode){
  this$static.selectionMode_0 = selectionMode;
}

function ContentDefinition_0(contentDisplayTextParam){
  this.contentDisplayText_0 = contentDisplayTextParam;
  $setFontColour(this, ($clinit_PredefinedContent() , fontColourDefault));
  $setFontStyle(this, fontStyleDefault);
  $setSelectionMode(this, selectionModeNone);
  this.displaySelectable_0 = true;
}

function ContentDefinition_1(contentDisplayTextParam, fontColourParam, fontStyleParam, selectionModeParam){
  this.contentDisplayText_0 = contentDisplayTextParam;
  this.fontColour_0 = fontColourParam;
  this.fontStyle_0 = fontStyleParam;
  this.selectionMode_0 = selectionModeParam;
  this.displaySelectable_0 = true;
}

defineSeed(61, 1, makeCastMap([Q$ContentDefinition]), ContentDefinition_0, ContentDefinition_1);
_.contentDisplayText_0 = null;
_.displaySelectable_0 = false;
_.fontColour_0 = null;
_.fontStyle_0 = null;
_.selectionMode_0 = null;
function FontStyle_0(styleParam){
  this.style_0 = styleParam;
}

defineSeed(62, 1, {}, FontStyle_0);
_.style_0 = 0;
function $clinit_PredefinedContent(){
  $clinit_PredefinedContent = nullMethod;
  fontStylePlain = new FontStyle_0(1);
  fontStyleUnderlined = new FontStyle_0(2);
  fontStyleBold = new FontStyle_0(3);
  fontStyleItalic = new FontStyle_0(4);
  fontStyleDefault = fontStylePlain;
  fontColourInputLanguage = new RGBColour_0(0, 0, 80);
  fontColourOutputLanguage = new RGBColour_0(0, 80, 20);
  fontColourPronunciation = new RGBColour_0(0, 128, 0);
  fontColourDefault = new RGBColour_0(0, 0, 0);
  selectionModeNone = new SelectionMode_0;
  selectionModeSingle = new SelectionMode_0;
  selectionModeAll = new SelectionMode_0;
  new RGBColour_0(255, 255, 255);
}

function addPredefinedContent(contentName, contentDisplayText, fontColour, fontStyle, seletionMode){
  var content_0;
  content_0 = new ContentDefinition_1(contentDisplayText, fontColour, fontStyle, seletionMode);
  $put(predefinedContentMap, 'content' + contentName, content_0);
  return content_0;
}

function getPredefinedContent(contentName){
  $clinit_PredefinedContent();
  var content_0;
  !predefinedContentMap && initPredefinedContent();
  content_0 = dynamicCast($get(predefinedContentMap, contentName), Q$ContentDefinition);
  if (!content_0)
    throw new DictionaryException_0('Predefined content not found: ' + contentName);
  return content_0;
}

function initPredefinedContent(){
  $clinit_PredefinedContent();
  predefinedContentMap = new Hashtable_0;
  addPredefinedContent('Definition', 'UIDisplayTextItems.contentDefault', fontColourDefault, fontStyleDefault, selectionModeNone);
  contentInputLanguage = addPredefinedContent('InputLanguage', 'UIDisplayTextItems.contentInputLanguage', fontColourInputLanguage, fontStyleBold, selectionModeNone);
  contentOutputLanguage = addPredefinedContent('OutputLanguage', 'UIDisplayTextItems.contentOutputLanguage', fontColourOutputLanguage, fontStyleDefault, selectionModeNone);
  contentNoDefinitionProvided = addPredefinedContent('NoDefinitionProvided', 'UIDisplayTextItems.contentNoDefinitionProvided', fontColourDefault, fontStyleDefault, selectionModeNone);
  addPredefinedContent('Pronunciation', 'UIDisplayTextItems.contentPronunciation', fontColourPronunciation, fontStyleDefault, selectionModeNone);
  addPredefinedContent('GrammaticalCategory', 'UIDisplayTextItems.contentGrammaticalCategory', new RGBColour_0(128, 0, 0), fontStyleDefault, selectionModeNone);
  addPredefinedContent('Notes', 'UIDisplayTextItems.contentExplanation', new RGBColour_0(128, 0, 128), fontStyleDefault, selectionModeNone);
  addPredefinedContent('Origin', 'UIDisplayTextItems.contentOrigin', new RGBColour_0(0, 128, 128), fontStyleDefault, selectionModeNone);
  addPredefinedContent('SampleUsage', 'UIDisplayTextItems.contentSampleUsage', new RGBColour_0(0, 0, 255), fontStyleDefault, selectionModeNone);
}

var contentInputLanguage = null, contentNoDefinitionProvided = null, contentOutputLanguage = null, fontColourDefault, fontColourInputLanguage, fontColourOutputLanguage, fontColourPronunciation, fontStyleBold, fontStyleDefault, fontStyleItalic, fontStylePlain, fontStyleUnderlined, predefinedContentMap = null, selectionModeAll, selectionModeNone, selectionModeSingle;
function $toHexString(value){
  return value <= 16?'0' + toString_8(value, 16):toString_8(value, 16);
}

function RGBColour_0(redParam, greenParam, blueParam){
  this.red = redParam;
  this.green = greenParam;
  this.blue = blueParam;
}

defineSeed(64, 1, {}, RGBColour_0);
_.getHexValue_0 = function getHexValue(){
  var hexValue;
  hexValue = new StringBuffer_0;
  $append_0(hexValue, $toHexString(this.red));
  $append_0(hexValue, $toHexString(this.green));
  $append_0(hexValue, $toHexString(this.blue));
  return hexValue.impl.toString_0(hexValue.data);
}
;
_.blue = 0;
_.green = 0;
_.red = 0;
function SelectionMode_0(){
}

defineSeed(65, 1, {}, SelectionMode_0);
defineSeed(66, 1, {});
function $read(this$static, b, off, len){
  var ch, ex, i;
  if (off < 0 || len < 0 || b.length - off < len)
    throw new IndexOutOfBoundsException_0;
  for (i = 0; i < len; ++i)
    try {
      if ((ch = $read_0(this$static)) < 0)
        return i == 0?-1:i;
      b[off + i] = ~~(ch << 24) >> 24;
    }
     catch ($e0) {
      $e0 = caught($e0);
      if (instanceOf($e0, Q$IOException)) {
        ex = $e0;
        if (i == 0)
          throw ex;
        return i;
      }
       else 
        throw $e0;
    }
  return i;
}

defineSeed(68, 1, {});
function $read_0(this$static){
  var character;
  if (this$static.currentPosition == this$static.fileContent.length)
    return -1;
  character = $charAt(this$static.fileContent, this$static.currentPosition) & 255;
  ++this$static.currentPosition;
  return character;
}

function $skip(this$static, n){
  var actualSkipped;
  lt(n, P0_longLit)?(actualSkipped = P0_longLit):gte_0(add(fromInt(this$static.currentPosition), n), fromInt(this$static.fileContent.length))?(actualSkipped = fromInt(this$static.fileContent.length - this$static.currentPosition)):(actualSkipped = n);
  this$static.currentPosition = toInt(add(fromInt(this$static.currentPosition), actualSkipped));
  return actualSkipped;
}

function HTRInputStream_0(fileName){
  this.fileContent = readFile(fileName, 'x-user-defined');
}

function readFile(fileName, charset){
  var readFileContent, url;
  url = baseDirectory + fileName;
  readFileContent = readFileJS(url, charset, htrStatusOK_0);
  if (readFileContent == null) {
    throw new DictionaryException_0('File data could not be read via XMLHttpRequest');
  }
  return readFileContent;
}

function readFileJS(url, charset, htrStatusOK){
  var isFirefoxBrowser = $wnd.navigator.userAgent.indexOf('Gecko/') != -1;
  var isFileReaderSupported = typeof FileReader != 'undefined';
  var isFileReaderSyncSupported = typeof FileReaderSync != 'undefined';
  var req = new XMLHttpRequest;
  var isResponseTypeSupported = req.responseType != undefined;
  req.open('GET', url, false);
  var mimeType = 'text/plain; charset=' + charset;
  req.overrideMimeType(mimeType);
  try {
    req.send(null);
  }
   catch (e) {
    alert('Exception bei XMLHttpRequest.send: ' + e);
  }
  if (req.status != htrStatusOK)
    return null;
  return req.responseText;
}

function setBaseDirectory(baseDirectoryParam){
  baseDirectory = baseDirectoryParam;
  baseDirectoryParam.toLowerCase().indexOf('http:') == 0?(htrStatusOK_0 = 200):(htrStatusOK_0 = 0);
}

defineSeed(67, 68, {}, HTRInputStream_0);
_.currentPosition = 0;
_.fileContent = null;
var baseDirectory = null, htrStatusOK_0 = 0;
function HTRInputStreamAccess_0(){
}

defineSeed(69, 66, {}, HTRInputStreamAccess_0);
function $createObjectForClass(this$static, className){
  var classNameWithoutPackagename, classObj;
  classObj = null;
  !this$static.normationClassTable && (this$static.normationClassTable = new Hashtable_0 , $put(this$static.normationClassTable, 'Normation', new Normation_0) , $put(this$static.normationClassTable, 'NormationBul', new NormationBul_0) , $put(this$static.normationClassTable, 'NormationCyr1', new NormationCyr1_0) , $put(this$static.normationClassTable, 'NormationCyr2', new NormationCyr2_0) , $put(this$static.normationClassTable, 'NormationEng', new NormationEng_0) , $put(this$static.normationClassTable, 'NormationEng2', new NormationEng2_0) , $put(this$static.normationClassTable, 'NormationEpo', new NormationEpo_0) , $put(this$static.normationClassTable, 'NormationFil', new NormationFil_0) , $put(this$static.normationClassTable, 'NormationGer', new NormationGer_0) , $put(this$static.normationClassTable, 'NormationJpn', new NormationJpn_0) , $put(this$static.normationClassTable, 'NormationLat', new NormationLat_0) , $put(this$static.normationClassTable, 'NormationNor', new NormationNor_0) , $put(this$static.normationClassTable, 'NormationRus', new NormationRus_0) , $put(this$static.normationClassTable, 'NormationRus2', new NormationRus2_0) , $put(this$static.normationClassTable, 'NormationRusC', new NormationRusC_0) , $put(this$static.normationClassTable, 'NormationUkr', new NormationUkr_0) , $put(this$static.normationClassTable, 'NormationUkrC', new NormationUkrC_0) , $put(this$static.normationClassTable, 'NormationVie', new NormationVie_0) , undefined);
  if (className.indexOf('de.kugihan.dictionaryformids.translation.normation.') == 0) {
    classNameWithoutPackagename = $substring(className, 51);
    classObj = $get(this$static.normationClassTable, classNameWithoutPackagename);
  }
  classObj == null && (classObj = null);
  return classObj;
}

defineSeed(70, 1, {});
_.normationClassTable = null;
function ClassMethodImpl_0(){
}

defineSeed(71, 70, {}, ClassMethodImpl_0);
function DictionaryException_0(message){
  Exception_0.call(this, message);
}

function DictionaryException_1(t){
  Exception_0.call(this, $toString(t));
}

defineSeed(73, 7, makeCastMap([Q$DictionaryException, Q$Serializable, Q$Exception, Q$Throwable]), DictionaryException_0, DictionaryException_1);
defineSeed(72, 73, makeCastMap([Q$DictionaryException, Q$Serializable, Q$Exception, Q$Throwable]));
function CouldNotOpenPropertyFileException_0(){
  DictionaryException_0.call(this, 'Property file could not be opened: DictionaryForMIDs.properties');
}

defineSeed(74, 72, makeCastMap([Q$DictionaryException, Q$Serializable, Q$Exception, Q$Throwable]), CouldNotOpenPropertyFileException_0);
function DictionaryClassNotLoadedException_0(message){
  DictionaryException_0.call(this, message);
}

defineSeed(75, 73, makeCastMap([Q$DictionaryException, Q$Serializable, Q$Exception, Q$Throwable]), DictionaryClassNotLoadedException_0);
function $implFindEntry(this$static, key){
  var entry, iter, k;
  for (iter = new AbstractHashMap$EntrySetIterator_0(this$static.entrySet().this$0); $hasNext(iter.iter);) {
    entry = dynamicCast($next(iter.iter), Q$Map$Entry);
    k = entry.getKey();
    if (key == null?k == null:equals__devirtual$(key, k)) {
      return entry;
    }
  }
  return null;
}

defineSeed(80, 1, makeCastMap([Q$Map]));
_.containsKey = function containsKey(key){
  return !!$implFindEntry(this, key);
}
;
_.equals$ = function equals_0(obj){
  var entry, entry$iterator, otherKey, otherMap, otherValue;
  if (obj === this) {
    return true;
  }
  if (!instanceOf(obj, Q$Map)) {
    return false;
  }
  otherMap = dynamicCast(obj, Q$Map);
  if (this.size_1() != otherMap.size_1()) {
    return false;
  }
  for (entry$iterator = new AbstractHashMap$EntrySetIterator_0(otherMap.entrySet().this$0); $hasNext(entry$iterator.iter);) {
    entry = dynamicCast($next(entry$iterator.iter), Q$Map$Entry);
    otherKey = entry.getKey();
    otherValue = entry.getValue();
    if (!this.containsKey(otherKey)) {
      return false;
    }
    if (!equalsWithNullCheck(otherValue, this.get(otherKey))) {
      return false;
    }
  }
  return true;
}
;
_.get = function get(key){
  var entry;
  entry = $implFindEntry(this, key);
  return !entry?null:entry.getValue();
}
;
_.hashCode$ = function hashCode_1(){
  var entry, entry$iterator, hashCode;
  hashCode = 0;
  for (entry$iterator = new AbstractHashMap$EntrySetIterator_0(this.entrySet().this$0); $hasNext(entry$iterator.iter);) {
    entry = dynamicCast($next(entry$iterator.iter), Q$Map$Entry);
    hashCode += entry.hashCode$();
    hashCode = ~~hashCode;
  }
  return hashCode;
}
;
_.size_1 = function size_0(){
  return this.entrySet().this$0.size_0;
}
;
_.toString$ = function toString_5(){
  var comma, entry, iter, s;
  s = '{';
  comma = false;
  for (iter = new AbstractHashMap$EntrySetIterator_0(this.entrySet().this$0); $hasNext(iter.iter);) {
    entry = dynamicCast($next(iter.iter), Q$Map$Entry);
    comma?(s += ', '):(comma = true);
    s += '' + entry.getKey();
    s += '=';
    s += '' + entry.getValue();
  }
  return s + '}';
}
;
function $addAllHashEntries(this$static, dest){
  var hashCodeMap = this$static.hashCodeMap;
  for (var hashCode in hashCodeMap) {
    var hashCodeInt = parseInt(hashCode, 10);
    if (hashCode == hashCodeInt) {
      var array = hashCodeMap[hashCodeInt];
      for (var i = 0, c = array.length; i < c; ++i) {
        dest.add(array[i]);
      }
    }
  }
}

function $addAllStringEntries(this$static, dest){
  var stringMap = this$static.stringMap;
  for (var key in stringMap) {
    if (key.charCodeAt(0) == 58) {
      var entry = new AbstractHashMap$MapEntryString_0(this$static, key.substring(1));
      dest.add(entry);
    }
  }
}

function $containsKey(this$static, key){
  return key == null?this$static.nullSlotLive:instanceOf(key, Q$String)?$hasStringValue(this$static, dynamicCast(key, Q$String)):$hasHashValue(this$static, key, this$static.getHashCode(key));
}

function $get(this$static, key){
  return key == null?this$static.nullSlot:instanceOf(key, Q$String)?$getStringValue(this$static, dynamicCast(key, Q$String)):$getHashValue(this$static, key, this$static.getHashCode(key));
}

function $getHashValue(this$static, key, hashCode){
  var array = this$static.hashCodeMap[hashCode];
  if (array) {
    for (var i = 0, c = array.length; i < c; ++i) {
      var entry = array[i];
      var entryKey = entry.getKey();
      if (this$static.equalsBridge(key, entryKey)) {
        return entry.getValue();
      }
    }
  }
  return null;
}

function $getStringValue(this$static, key){
  return this$static.stringMap[':' + key];
}

function $hasHashValue(this$static, key, hashCode){
  var array = this$static.hashCodeMap[hashCode];
  if (array) {
    for (var i = 0, c = array.length; i < c; ++i) {
      var entry = array[i];
      var entryKey = entry.getKey();
      if (this$static.equalsBridge(key, entryKey)) {
        return true;
      }
    }
  }
  return false;
}

function $hasStringValue(this$static, key){
  return ':' + key in this$static.stringMap;
}

function $put(this$static, key, value){
  return key == null?$putNullSlot(this$static, value):key != null?$putStringValue(this$static, key, value):$putHashValue(this$static, null, value, ~~getHashCode_1(null));
}

function $putHashValue(this$static, key, value, hashCode){
  var array = this$static.hashCodeMap[hashCode];
  if (array) {
    for (var i = 0, c = array.length; i < c; ++i) {
      var entry = array[i];
      var entryKey = entry.getKey();
      if (this$static.equalsBridge(key, entryKey)) {
        var previous = entry.getValue();
        entry.setValue(value);
        return previous;
      }
    }
  }
   else {
    array = this$static.hashCodeMap[hashCode] = [];
  }
  var entry = new MapEntryImpl_0(key, value);
  array.push(entry);
  ++this$static.size_0;
  return null;
}

function $putNullSlot(this$static, value){
  var result;
  result = this$static.nullSlot;
  this$static.nullSlot = value;
  if (!this$static.nullSlotLive) {
    this$static.nullSlotLive = true;
    ++this$static.size_0;
  }
  return result;
}

function $putStringValue(this$static, key, value){
  var result, stringMap = this$static.stringMap;
  key = ':' + key;
  key in stringMap?(result = stringMap[key]):++this$static.size_0;
  stringMap[key] = value;
  return result;
}

defineSeed(79, 80, makeCastMap([Q$Map]));
_.containsKey = function containsKey_0(key){
  return $containsKey(this, key);
}
;
_.entrySet = function entrySet(){
  return new AbstractHashMap$EntrySet_0(this);
}
;
_.equalsBridge = function equalsBridge(value1, value2){
  return this.equals(value1, value2);
}
;
_.get = function get_0(key){
  return $get(this, key);
}
;
_.size_1 = function size_1(){
  return this.size_0;
}
;
_.hashCodeMap = null;
_.nullSlot = null;
_.nullSlotLive = false;
_.size_0 = 0;
_.stringMap = null;
defineSeed(78, 79, makeCastMap([Q$Serializable, Q$Map]));
_.equals = function equals_1(value1, value2){
  return maskUndefined(value1) === maskUndefined(value2) || value1 != null && equals__devirtual$(value1, value2);
}
;
_.getHashCode = function getHashCode_0(key){
  return ~~hashCode__devirtual$(key);
}
;
function Hashtable_0(){
  this.hashCodeMap = [];
  this.stringMap = {};
  this.nullSlotLive = false;
  this.nullSlot = null;
  this.size_0 = 0;
}

defineSeed(77, 78, makeCastMap([Q$Serializable, Q$Map]), Hashtable_0);
function $getProperty(this$static, key){
  var prop, value;
  prop = this$static;
  do {
    value = dynamicCast(key == null?prop.nullSlot:key != null?prop.stringMap[':' + key]:$getHashValue(prop, null, ~~hashCode__devirtual$(null)), Q$String);
    if (value != null)
      return value;
    prop = prop.defaults;
  }
   while (prop);
  return null;
}

function $load(this$static, inStream){
  var c, element, isDelim, key, line, pos, reader, uni;
  reader = new InputStreamReader_0(inStream, this$static.propertyCharEncoding);
  while ((line = $readLine(this$static, reader)) != null) {
    c = 0;
    pos = 0;
    while (pos < line.length && isWhitespace(c = line.charCodeAt(pos)))
      ++pos;
    if (line.length - pos == 0 || line.charCodeAt(pos) == 35 || line.charCodeAt(pos) == 33)
      continue;
    key = new StringBuffer_0;
    while (pos < line.length && !isWhitespace(c = $charAt(line, pos++)) && c != 61 && c != 58) {
      if (c == 92) {
        if (pos == line.length) {
          line = $readLine(this$static, reader);
          pos = 0;
          while (pos < line.length && isWhitespace(c = line.charCodeAt(pos)))
            ++pos;
        }
         else {
          c = $charAt(line, pos++);
          switch (c) {
            case 110:
              key.impl.appendNonNull(key.data, '\n');
              break;
            case 116:
              key.impl.appendNonNull(key.data, '\t');
              break;
            case 114:
              key.impl.appendNonNull(key.data, '\r');
              break;
            case 117:
              if (pos + 4 <= line.length) {
                uni = __parseAndValidateInt(line.substr(pos, pos + 4 - pos), 16) & 65535;
                key.impl.appendNonNull(key.data, String.fromCharCode(uni));
                pos += 4;
              }

              break;
            default:key.impl.appendNonNull(key.data, String.fromCharCode(c));
          }
        }
      }
       else 
        key.impl.appendNonNull(key.data, String.fromCharCode(c));
    }
    isDelim = c == 58 || c == 61;
    while (pos < line.length && isWhitespace(c = line.charCodeAt(pos)))
      ++pos;
    if (!isDelim && (c == 58 || c == 61)) {
      ++pos;
      while (pos < line.length && isWhitespace(line.charCodeAt(pos)))
        ++pos;
    }
    element = new StringBuffer_1(line.length - pos);
    while (pos < line.length) {
      c = $charAt(line, pos++);
      if (c == 92) {
        if (pos == line.length) {
          line = $readLine(this$static, reader);
          if (line == null)
            break;
          pos = 0;
          while (pos < line.length && isWhitespace(line.charCodeAt(pos)))
            ++pos;
          line.length - pos + element.impl.length_0(element.data);
        }
         else {
          c = $charAt(line, pos++);
          switch (c) {
            case 110:
              element.impl.appendNonNull(element.data, '\n');
              break;
            case 116:
              element.impl.appendNonNull(element.data, '\t');
              break;
            case 114:
              element.impl.appendNonNull(element.data, '\r');
              break;
            case 117:
              if (pos + 4 <= line.length) {
                uni = __parseAndValidateInt(line.substr(pos, pos + 4 - pos), 16) & 65535;
                element.impl.appendNonNull(element.data, String.fromCharCode(uni));
                pos += 4;
              }

              break;
            default:element.impl.appendNonNull(element.data, String.fromCharCode(c));
          }
        }
      }
       else 
        element.impl.appendNonNull(element.data, String.fromCharCode(c));
    }
    $put(this$static, key.impl.toString_0(key.data), element.impl.toString_0(element.data));
  }
}

function $readLine(this$static, isr){
  var endOfLineReached, readCharacter, readLine, readLineReturnValue, readValue;
  readLine = new StringBuffer_0;
  endOfLineReached = false;
  do {
    readValue = $read_1(isr);
    if (readValue == -1) {
      endOfLineReached = true;
    }
     else {
      readCharacter = readValue & 65535;
      if (readCharacter == 10) {
        if (this$static.lastCharacterWasCR)
        ;
        else {
          endOfLineReached = true;
        }
        this$static.lastCharacterWasCR = false;
      }
       else if (readCharacter == 13) {
        endOfLineReached = true;
        this$static.lastCharacterWasCR = true;
      }
       else {
        this$static.lastCharacterWasCR = false;
        readLine.impl.appendNonNull(readLine.data, String.fromCharCode(readCharacter));
      }
    }
  }
   while (!endOfLineReached);
  readValue == -1 && readLine.impl.length_0(readLine.data) == 0?(readLineReturnValue = null):(readLineReturnValue = readLine.impl.toString_0(readLine.data));
  return readLineReturnValue;
}

function Properties_0(){
  Hashtable_0.call(this);
  this.propertyCharEncoding = this.propertyCharEncoding;
}

defineSeed(76, 77, makeCastMap([Q$Serializable, Q$Map]), Properties_0);
_.defaults = null;
_.lastCharacterWasCR = false;
_.propertyCharEncoding = 'ISO-8859-1';
function $convertFieldAndLineSeparatorChars(text){
  var currentCharacter, pos, textlength;
  pos = 0;
  textlength = text.impl.length_0(text.data);
  while (pos < textlength) {
    currentCharacter = $charAt(text.impl.toString_0(text.data), pos);
    if (currentCharacter == 92) {
      ++pos;
      if (pos < textlength) {
        currentCharacter = $charAt(text.impl.toString_0(text.data), pos);
        if (currentCharacter == 92) {
          text.impl.replace_0(text.data, pos, pos + 1, '');
        }
         else if (currentCharacter == 110) {
          text.impl.replace_0(text.data, pos - 1, pos - 1 + 1, '\n');
          text.impl.replace_0(text.data, pos, pos + 1, '');
        }
         else if (currentCharacter == 116) {
          text.impl.replace_0(text.data, pos - 1, pos - 1 + 1, '\t');
          text.impl.replace_0(text.data, pos, pos + 1, '');
        }
      }
      textlength = text.impl.length_0(text.data);
    }
     else {
      ++pos;
    }
  }
}

function $getBooleanFromProperty(this$static, propertyName, propertyValue){
  var errorText, returnValueBoolean;
  if (stringEqualIgnoreCase(propertyValue, 'true')) {
    returnValueBoolean = true;
  }
   else if (stringEqualIgnoreCase(propertyValue, 'false')) {
    returnValueBoolean = false;
  }
   else {
    errorText = 'Property ' + propertyName + ' must be true or false';
    this$static.logLevel >= 0 && (UtilJs.outputMessage(errorText + '\n') , undefined);
    throw new DictionaryException_0(errorText);
  }
  return returnValueBoolean;
}

function $getCharValueFromProperty(this$static, propertyName, propertyValue){
  var charPropertyNotWellFormed, errorText, extractedValue, propertyValueChar;
  propertyValueChar = 9;
  if (propertyValue.indexOf("'") != 0 || !$endsWith(propertyValue, "'")) {
    errorText = 'Property ' + propertyName + " must start with ' and end with ' ";
    this$static.logLevel >= 0 && (UtilJs.outputMessage(errorText + '\n') , undefined);
    throw new DictionaryException_0(errorText);
  }
  extractedValue = $substring_0(propertyValue, 1, propertyValue.length - 1);
  charPropertyNotWellFormed = false;
  extractedValue.indexOf('\\') == 0?extractedValue.length != 2?(charPropertyNotWellFormed = true):$endsWith(extractedValue, 't')?(propertyValueChar = 9):(charPropertyNotWellFormed = true):extractedValue.length != 1?(charPropertyNotWellFormed = true):(propertyValueChar = extractedValue.charCodeAt(0));
  if (charPropertyNotWellFormed) {
    errorText = 'Property ' + propertyName + ' must contain one character or \\t';
    this$static.logLevel >= 0 && (UtilJs.outputMessage(errorText + '\n') , undefined);
    throw new DictionaryException_0(errorText);
  }
  return propertyValueChar;
}

function $getDictionaryPropertyBooleanDefault(this$static, propertyName, defaultValue){
  var propertyValue, returnValueBoolean;
  propertyValue = $getDictionaryPropertyString(this$static, propertyName, true);
  propertyValue != null?(returnValueBoolean = $getBooleanFromProperty(this$static, propertyName, propertyValue)):(returnValueBoolean = defaultValue);
  return returnValueBoolean;
}

function $getDictionaryPropertyChar(this$static, propertyName){
  var propertyValue, propertyValueChar;
  propertyValue = $getDictionaryPropertyString(this$static, propertyName, false);
  propertyValueChar = $getCharValueFromProperty(this$static, propertyName, propertyValue);
  return propertyValueChar;
}

function $getDictionaryPropertyIntDefault(this$static, propertyName, defaultValue){
  var propertyValue, returnValueInt;
  propertyValue = $getDictionaryPropertyString(this$static, propertyName, true);
  propertyValue != null?(returnValueInt = valueOf(__parseAndValidateInt(propertyValue, 10)).value):(returnValueInt = defaultValue);
  return returnValueInt;
}

function $getDictionaryPropertyString(this$static, propertyName, optional){
  var propertyValue, propertyValue_0;
  propertyValue = (propertyValue_0 = $getProperty(this$static.dictionaryForMIDsProperties, propertyName) , propertyValue_0 != null && (propertyValue_0 = $trim(propertyValue_0)) , propertyValue_0);
  propertyName != null && propertyName.length == 0 && (propertyName = null);
  propertyValue == null && !optional && $propertyNotFound(propertyName);
  return propertyValue;
}

function $log(this$static, logMessage){
  this$static.logLevel >= 0 && (UtilJs.outputMessage(logMessage + '\n') , undefined);
}

function $log_0(this$static, logMessage, logLevelOutput){
  this$static.logLevel >= logLevelOutput && (UtilJs.outputMessage(logMessage + '\n') , undefined);
}

function $logDebug(this$static, logMessage){
  this$static.logLevel >= 1 && (UtilJs.outputMessage(logMessage + '\n') , undefined);
}

function $logTime(this$static, message, startTime){
  var endTime, executionTime;
  endTime = fromDouble(currentTimeMillis0());
  executionTime = sub_0(endTime, startTime);
  this$static.logLevel >= 2 && $outputMessage('Time for ' + message + ': ' + toString_4(executionTime) + '\n');
}

function $openProperties(this$static){
  var propertyStream;
  this$static.dictionaryForMIDsProperties = new Properties_0;
  try {
    propertyStream = new HTRInputStream_0('/dictionary/DictionaryForMIDs.properties');
  }
   catch ($e0) {
    $e0 = caught($e0);
    if (instanceOf($e0, Q$DictionaryException)) {
      throw new CouldNotOpenPropertyFileException_0;
    }
     else 
      throw $e0;
  }
  try {
    $load(this$static.dictionaryForMIDsProperties, propertyStream);
  }
   catch ($e0) {
    $e0 = caught($e0);
    if (instanceOf($e0, Q$IOException)) {
      throw new DictionaryException_0('Property file could not be read DictionaryForMIDs.properties');
    }
     else 
      throw $e0;
  }
}

function $propertyNotFound(propertyName){
  var errorText;
  errorText = 'Property ' + propertyName + ' not found';
  throw new DictionaryException_0(errorText);
}

function charIsLineWhitespace(character){
  var returnValue_0;
  character == 9 || character == 32?(returnValue_0 = true):(returnValue_0 = false);
  return returnValue_0;
}

function convertToLowerCase(string){
  var charPos, lowerCaseWord;
  lowerCaseWord = new StringBuffer_0;
  for (charPos = 0; charPos < string.impl.length_0(string.data); ++charPos) {
    $append(lowerCaseWord, toLowerCase($charAt(string.impl.toString_0(string.data), charPos)));
  }
  return lowerCaseWord;
}

function filterSuperflousWhitespaces(expression){
  var charPos, character, expressionLength, lastCharWasWhitespace, resultExpression;
  expressionLength = expression.impl.length_0(expression.data);
  resultExpression = new StringBuffer_1;
  lastCharWasWhitespace = false;
  for (charPos = 0; charPos < expressionLength; ++charPos) {
    character = $charAt(expression.impl.toString_0(expression.data), charPos);
    if (charIsLineWhitespace(character)) {
      if (resultExpression.impl.length_0(resultExpression.data) == 0)
      ;
      else if (charPos == expression.impl.length_0(expression.data) - 1)
      ;
      else if (lastCharWasWhitespace)
      ;
      else {
        resultExpression.impl.appendNonNull(resultExpression.data, String.fromCharCode(character));
      }
      lastCharWasWhitespace = true;
    }
     else {
      resultExpression.impl.appendNonNull(resultExpression.data, String.fromCharCode(character));
      lastCharWasWhitespace = false;
    }
  }
  return resultExpression;
}

function isSeparatorCharacter_0(character){
  return charIsLineWhitespace(character) || $indexOf('!"$\xA7$%&/()=?\xB4`\\{}[]^\xB0+*~#\'-_.:,;<>|@', fromCodePoint(character)) != -1;
}

function isWhitespace(character){
  var returnValue_0;
  character == 9 || character == 10 || character == 32 || character == 12 || character == 28 || character == 29 || character == 30 || character == 31?(returnValue_0 = true):(returnValue_0 = false);
  return returnValue_0;
}

function removePunctuation(string, exceptSearchSpecialCharacters){
  var charFromString, charPos, noPunctuationWord;
  noPunctuationWord = new StringBuffer_0;
  for (charPos = 0; charPos < string.impl.length_0(string.data); ++charPos) {
    charFromString = $charAt(string.impl.toString_0(string.data), charPos);
    $indexOf('!"$\xA7$%&/()=?\xB4`\\{}[]^\xB0+*~#\'-_.:,;<>|@', fromCodePoint(charFromString)) == -1 || (charFromString == 42 || charFromString == 63) && exceptSearchSpecialCharacters?(noPunctuationWord.impl.appendNonNull(noPunctuationWord.data, String.fromCharCode(charFromString)) , noPunctuationWord):(noPunctuationWord.impl.appendNonNull(noPunctuationWord.data, ' ') , noPunctuationWord);
  }
  return noPunctuationWord;
}

function removeSuperflousSearchCharacters(toBeTranslatedWord){
  var charPos, result;
  result = new StringBuffer_2(toBeTranslatedWord);
  while (result.impl.length_0(result.data) >= 2) {
    if (result.impl.toString_0(result.data).charCodeAt(0) == 47 && result.impl.toString_0(result.data).charCodeAt(1) == 47) {
      result.impl.replace_0(result.data, 0, 1, '');
    }
     else if ($charAt_0(result, result.impl.length_0(result.data) - 1) == 47 && $charAt_0(result, result.impl.length_0(result.data) - 2) == 47) {
      $deleteCharAt(result, result.impl.length_0(result.data) - 1);
    }
     else {
      break;
    }
  }
  result.impl.length_0(result.data) == 1 && result.impl.toString_0(result.data).charCodeAt(0) == 47 && (result.impl.replace_0(result.data, 0, 1, '') , result);
  charPos = 0;
  while (result.impl.length_0(result.data) > charPos + 1) {
    $charAt(result.impl.toString_0(result.data), charPos) == 42 && $charAt(result.impl.toString_0(result.data), charPos + 1) == 42?(result.impl.replace_0(result.data, charPos, charPos + 1, '') , result):++charPos;
  }
  return result.impl.toString_0(result.data);
}

function stringEqualIgnoreCase(string1, string2){
  var pos, stringLength, stringsAreEqual;
  stringsAreEqual = false;
  if (string1.length == string2.length) {
    stringLength = string1.length;
    for (pos = 0; pos < stringLength; ++pos) {
      if (toUpperCase(string1.charCodeAt(pos)) != toUpperCase(string2.charCodeAt(pos))) {
        break;
      }
    }
    pos == stringLength && (stringsAreEqual = true);
  }
  return stringsAreEqual;
}

function stringSplit(stringToSplit){
  var charCount, charLastPos, elementCount, numberOfElements, stringElements, stringToSplitLength;
  numberOfElements = 1;
  stringToSplitLength = stringToSplit.length;
  for (charCount = 0; charCount < stringToSplitLength; ++charCount) {
    stringToSplit.charCodeAt(charCount) == 44 && ++numberOfElements;
  }
  stringElements = initDim(_3Ljava_lang_String_2_classLit, makeCastMap([Q$Serializable]), Q$String, numberOfElements, 0);
  elementCount = 0;
  charLastPos = 0;
  for (charCount = 0; charCount <= stringToSplitLength; ++charCount) {
    if (charCount == stringToSplitLength || stringToSplit.charCodeAt(charCount) == 44) {
      if (charLastPos < stringToSplitLength) {
        stringElements[elementCount] = stringToSplit.substr(charLastPos, charCount - charLastPos);
        charLastPos = charCount + 1;
      }
       else {
        stringElements[elementCount] = '';
      }
      ++elementCount;
    }
  }
  return stringElements;
}

defineSeed(81, 1, {});
_.log_0 = function log(logMessage, logLevelOutput){
  $log_0(this, logMessage, logLevelOutput);
}
;
_.setLogLevel_0 = function setLogLevel(logLevelParam){
  this.logLevel = logLevelParam;
}
;
_.dictionaryForMIDsProperties = null;
_.logLevel = 0;
var utilObj_0 = null;
function $exportStaticClasses(this$static){
  function outputMessageToConsole(message){
    console.log(message);
  }

  $wnd.UtilJs = new Object;
  var utilJs = $wnd.UtilJs;
  utilJs.setLogLevel = $entry(this$static.setLogLevel_0);
  utilJs.log = $entry(this$static.log_0);
  UtilJs.outputMessage = outputMessageToConsole;
}

function $outputMessage(message){
  UtilJs.outputMessage(message);
}

function UtilJs_1(){
  new ClassMethodImpl_0;
}

defineSeed(82, 81, {}, UtilJs_1);
function $addStringColourItemTextPart(this$static){
  var itemTextPart, topContent;
  if ($length(this$static.text) > 0) {
    topContent = dynamicCast($peek(this$static.contentHierarchy), Q$ContentDefinition);
    itemTextPart = $getItemTextPartFromContent($toString_1(this$static.text), topContent);
    $addItemTextPart(this$static.stringColourItemText, itemTextPart);
  }
  $setLength(this$static.text, 0);
}

function $determineItemsFromContent(this$static, contentText, changeInputAndOutputContent, isInput){
  var charCount, contentChar, contentDefinitionAvailable, contentNumber, contentString, contentStringLength, contents, nextChar;
  contents = contentText.dictionary_0.supportedLanguages_0[contentText.languageIndex].contents_0;
  contentDefinitionAvailable = contentText.dictionary_0.supportedLanguages_0[contentText.languageIndex].contentDefinitionAvailable_0;
  contentString = contentText.text;
  this$static.stringColourItemText = new StringColourItemText_0;
  $pushNewContent(this$static, contents[0], changeInputAndOutputContent, isInput);
  contentStringLength = contentString.length;
  charCount = 0;
  while (charCount < contentStringLength) {
    contentChar = contentString.charCodeAt(charCount);
    if (contentDefinitionAvailable) {
      if (contentChar == 91) {
        charCount + 2 >= contentStringLength && $throwContentFormatException('Start of content ([) without complete content number');
        contentNumber = $getStartContentDigitValue(contentString.charCodeAt(charCount + 1)) * 10 + $getStartContentDigitValue(contentString.charCodeAt(charCount + 2));
        charCount += 2;
        $addStringColourItemTextPart(this$static);
        (contentNumber < 1 || contentNumber >= contents.length) && $throwContentFormatException('Incorrect content number: ' + contentNumber);
        $pushNewContent(this$static, contents[contentNumber], changeInputAndOutputContent, isInput);
      }
       else if (contentChar == 93) {
        $addStringColourItemTextPart(this$static);
        $pop(this$static.contentHierarchy);
        this$static.contentHierarchy.arrayList.size_0 == 0 && $throwContentFormatException('End of content without start of content');
      }
       else if (contentChar == 92) {
        if (charCount + 1 < contentStringLength) {
          nextChar = contentString.charCodeAt(charCount + 1);
          if (nextChar == 91 || nextChar == 93) {
            ++charCount;
            contentChar = nextChar;
          }
          $append(this$static.text, contentChar);
        }
      }
       else {
        $append(this$static.text, contentChar);
      }
    }
     else {
      $append(this$static.text, contentChar);
    }
    ++charCount;
  }
  $addStringColourItemTextPart(this$static);
  return this$static.stringColourItemText;
}

function $getItemTextPartFromContent(itemTextPartString, content_0){
  var itemTextPart;
  itemTextPart = new StringColourItemTextPart_0(itemTextPartString, content_0.fontColour_0, content_0.fontStyle_0);
  return itemTextPart;
}

function $getStartContentDigitValue(charParam){
  null != String.fromCharCode(charParam).match(/\d/) || $throwContentFormatException('Start of content ([) is not followed by 2 digits');
  return digit(charParam, 10);
}

function $pushNewContent(this$static, newContent, changeInputAndOutputContent, isInput){
  changeInputAndOutputContent && newContent == ($clinit_PredefinedContent() , $clinit_PredefinedContent() , !predefinedContentMap && initPredefinedContent() , $clinit_PredefinedContent() , contentNoDefinitionProvided) && (isInput?(newContent = ($clinit_PredefinedContent() , $clinit_PredefinedContent() , !predefinedContentMap && initPredefinedContent() , $clinit_PredefinedContent() , contentInputLanguage)):(newContent = ($clinit_PredefinedContent() , $clinit_PredefinedContent() , !predefinedContentMap && initPredefinedContent() , $clinit_PredefinedContent() , contentOutputLanguage)));
  $add_1(this$static.contentHierarchy, newContent);
  $setLength(this$static.text, 0);
}

function $throwContentFormatException(message){
  throw new DictionaryException_0('Error in content format: ' + message);
}

function ContentParser_1(){
  this.contentHierarchy = new Stack_0;
  this.text = new StringBuffer_0;
}

defineSeed(83, 1, {}, ContentParser_1);
_.stringColourItemText = null;
function $addItemTextPart(this$static, itemTextPartParam){
  $add_1(this$static.itemTextParts, itemTextPartParam);
}

function StringColourItemText_0(){
  this.itemTextParts = new Vector_0;
}

defineSeed(84, 1, {}, StringColourItemText_0);
_.getItemTextPart_0 = function getItemTextPart(index){
  return dynamicCast($get_1(this.itemTextParts, index), Q$StringColourItemTextPart);
}
;
_.size_1 = function size_2(){
  return this.itemTextParts.arrayList.size_0;
}
;
function StringColourItemTextPart_0(textParam, colourParam, styleParam){
  this.text = textParam;
  this.colour = colourParam;
  this.style_0 = styleParam;
}

defineSeed(85, 1, makeCastMap([Q$StringColourItemTextPart]), StringColourItemTextPart_0);
_.getColour_0 = function getColour(){
  return this.colour;
}
;
_.getStyle_0 = function getStyle(){
  return this.style_0;
}
;
_.getText_0 = function getText(){
  return this.text;
}
;
_.colour = null;
_.style_0 = null;
_.text = null;
function $compareTo(this$static, otherDirectoryFileLocation){
  var compareResult, directoryFileNumberCompared, positionInDirectoryFileCompared, postfixDictionaryFileCompared;
  postfixDictionaryFileCompared = compareTo(this$static.postfixDictionaryFile, otherDirectoryFileLocation.postfixDictionaryFile);
  if (postfixDictionaryFileCompared == 0) {
    directoryFileNumberCompared = this$static.directoryFileNumber - otherDirectoryFileLocation.directoryFileNumber;
    if (directoryFileNumberCompared == 0) {
      positionInDirectoryFileCompared = this$static.positionInDirectoryFile - otherDirectoryFileLocation.positionInDirectoryFile;
      compareResult = positionInDirectoryFileCompared;
    }
     else {
      compareResult = directoryFileNumberCompared;
    }
  }
   else {
    compareResult = postfixDictionaryFileCompared;
  }
  return compareResult;
}

function DirectoryFileLocation_0(directoryFileNumberParam, postfixDictionaryFileParam, positionInDirectoryFileParam){
  this.directoryFileNumber = directoryFileNumberParam;
  this.postfixDictionaryFile = postfixDictionaryFileParam;
  this.positionInDirectoryFile = positionInDirectoryFileParam;
}

defineSeed(86, 1, {}, DirectoryFileLocation_0);
_.directoryFileNumber = 0;
_.positionInDirectoryFile = 0;
_.postfixDictionaryFile = null;
function SearchIndicator_0(searchIndicatorCharacter){
  if (searchIndicatorCharacter == 66)
    this.beginOfExpression = true;
  else if (searchIndicatorCharacter == 83)
    this.beginOfExpression = false;
  else 
    throw new DictionaryException_0('Illegal value for searchindicator');
}

defineSeed(87, 1, {}, SearchIndicator_0);
_.beginOfExpression = false;
function SearchedWord_0(wordParam){
  this.word = wordParam;
}

defineSeed(88, 1, makeCastMap([Q$SearchedWord]), SearchedWord_0);
_.word = null;
function $compareTo_0(this$static, otherSingleTranslation){
  var compareResult, directoryFileLocationCompared, internalSortNumberCompared, primarySortNumberCompared;
  directoryFileLocationCompared = $compareTo(this$static.directoryFileLocation, otherSingleTranslation.directoryFileLocation);
  if (directoryFileLocationCompared == 0 && this$static.fromText.languageIndex == otherSingleTranslation.fromText.languageIndex) {
    compareResult = 0;
  }
   else {
    primarySortNumberCompared = this$static.primarySortNumber - otherSingleTranslation.primarySortNumber;
    if (primarySortNumberCompared == 0) {
      if (this$static.foundAtBeginOfExpression == otherSingleTranslation.foundAtBeginOfExpression) {
        internalSortNumberCompared = this$static.internalSortNumber - otherSingleTranslation.internalSortNumber;
        internalSortNumberCompared == 0?(compareResult = directoryFileLocationCompared):(compareResult = internalSortNumberCompared);
      }
       else {
        this$static.foundAtBeginOfExpression?(compareResult = -1):(compareResult = 1);
      }
    }
     else {
      compareResult = primarySortNumberCompared;
    }
  }
  return compareResult;
}

function $determineInternalSortNumber(this$static){
  var charCount, inSeparator, isSeparatorCharacter, numberOfWords;
  numberOfWords = 1;
  inSeparator = false;
  for (charCount = 0; charCount < this$static.fromText.text.length; ++charCount) {
    isSeparatorCharacter = isSeparatorCharacter_0($charAt(this$static.fromText.text, charCount));
    inSeparator || isSeparatorCharacter && ++numberOfWords;
    inSeparator = isSeparatorCharacter;
  }
  this$static.internalSortNumber = numberOfWords;
}

function SingleTranslation_0(fromTextParam, toTextsParam, foundAtBeginOfExpressionParam, primarySortNumberParam, directoryFileLocationParam){
  this.fromText = fromTextParam;
  this.toTexts = toTextsParam;
  this.foundAtBeginOfExpression = foundAtBeginOfExpressionParam;
  this.primarySortNumber = primarySortNumberParam;
  this.directoryFileLocation = directoryFileLocationParam;
  $determineInternalSortNumber(this);
}

defineSeed(89, 1, makeCastMap([Q$SingleTranslation]), SingleTranslation_0);
_.getFromText_0 = function getFromText(){
  return this.fromText;
}
;
_.getNumberOfToTexts_0 = function getNumberOfToTexts(){
  return this.toTexts.arrayList.size_0;
}
;
_.getToTextAt_0 = function getToTextAt(index){
  return dynamicCast($elementAt(this.toTexts, index), Q$TextOfLanguage);
}
;
_.directoryFileLocation = null;
_.foundAtBeginOfExpression = false;
_.fromText = null;
_.internalSortNumber = 0;
_.primarySortNumber = 0;
_.toTexts = null;
function TextOfLanguage_0(text, languageIndex, dictionaryParam){
  this.text = text;
  this.languageIndex = languageIndex;
  this.dictionary_0 = dictionaryParam;
}

defineSeed(90, 1, makeCastMap([Q$TextOfLanguage]), TextOfLanguage_0);
_.getLanguageIndex_0 = function getLanguageIndex(){
  return this.languageIndex;
}
;
_.getText_0 = function getText_0(){
  return this.text;
}
;
_.dictionary_0 = null;
_.languageIndex = 0;
_.text = null;
function $clinit_Translation(){
  var charArr;
  $clinit_Translation = nullMethod;
  weakEncrypt_password = (charArr = initDim(_3C_classLit, makeCastMap([Q$Serializable]), -1, 18, 1) , $getChars(charArr, 0) , charArr);
}

function $addTranslation(this$static, fromText, toTexts, foundAtBeginOfExpression, directoryFileLocation){
  var indexTranslation, newSingleTranslation, numberOfTranslations, translation, translationsCompared;
  newSingleTranslation = new SingleTranslation_0(fromText, toTexts, foundAtBeginOfExpression, fromText.languageIndex, directoryFileLocation);
  indexTranslation = 0;
  numberOfTranslations = this$static.resultOfTranslation.translations.arrayList.size_0;
  while (indexTranslation < numberOfTranslations) {
    translation = dynamicCast($elementAt(this$static.resultOfTranslation.translations, indexTranslation), Q$SingleTranslation);
    translationsCompared = $compareTo_0(newSingleTranslation, translation);
    if (translationsCompared == 0) {
      break;
    }
     else if (translationsCompared < 0) {
      $insertTranslationAt(this$static.resultOfTranslation, newSingleTranslation, indexTranslation);
      break;
    }
    ++indexTranslation;
  }
  indexTranslation == numberOfTranslations && $addTranslation_0(this$static.resultOfTranslation, newSingleTranslation);
}

function $getDictionaryEntry(this$static, inputLanguageForSearch, indexStringLine){
  var directoryFileLocation, directoryFileNumber, directoryFileNumberString, indexString, posFirstCharIndexString, posIndexFileSeparatorFileNumberToPosition, posIndexFileSeparatorFilePositionToSearchIndicator, posIndexFileSeparatorIndexEntries, posLastCharIndexString, positionInDirectoryFile, postfixDictionaryFile, searchIndicatorObj, supportedLanguage;
  posFirstCharIndexString = 0;
  do {
    posIndexFileSeparatorIndexEntries = $indexOf_0(indexStringLine, fromCodePoint(44), posFirstCharIndexString);
    posIndexFileSeparatorIndexEntries == -1?(posLastCharIndexString = indexStringLine.length):(posLastCharIndexString = posIndexFileSeparatorIndexEntries);
    indexString = indexStringLine.substr(posFirstCharIndexString, posLastCharIndexString - posFirstCharIndexString);
    posFirstCharIndexString = posLastCharIndexString + 1;
    posIndexFileSeparatorFileNumberToPosition = $indexOf(indexString, fromCodePoint(45));
    directoryFileNumberString = indexString.substr(0, posIndexFileSeparatorFileNumberToPosition - 0);
    directoryFileNumber = __parseAndValidateInt(directoryFileNumberString, 10);
    posIndexFileSeparatorFilePositionToSearchIndicator = $indexOf_0(indexString, fromCodePoint(45), posIndexFileSeparatorFileNumberToPosition + 1);
    if (posIndexFileSeparatorFilePositionToSearchIndicator == -1) {
      throw new DictionaryException_0('Indexfile has no searchindicator - use DictionaryGeneration 2.4.4 or newer');
    }
    positionInDirectoryFile = valueOf(__parseAndValidateInt(indexString.substr(posIndexFileSeparatorFileNumberToPosition + 1, posIndexFileSeparatorFilePositionToSearchIndicator - (posIndexFileSeparatorFileNumberToPosition + 1)), 10)).value;
    searchIndicatorObj = new SearchIndicator_0(indexString.charCodeAt(posIndexFileSeparatorFilePositionToSearchIndicator + 1));
    if (this$static.searchSubExpressionStart || searchIndicatorObj.beginOfExpression) {
      supportedLanguage = this$static.dictionary_0.supportedLanguages_0[inputLanguageForSearch];
      supportedLanguage.hasSeparateDictionaryFile?(postfixDictionaryFile = supportedLanguage.languageFilePostfix_0):(postfixDictionaryFile = '');
      if (this$static.resultOfTranslation.translations.arrayList.size_0 >= this$static.maxHits || gte_0(sub_0(fromDouble(currentTimeMillis0()), this$static.startTime), fromInt(this$static.durationForCancelSearch)) || this$static.translationIsCancelled) {
        break;
      }
       else {
        directoryFileLocation = new DirectoryFileLocation_0(directoryFileNumber, postfixDictionaryFile, positionInDirectoryFile);
        $getTranslation(this$static, inputLanguageForSearch, directoryFileLocation, searchIndicatorObj.beginOfExpression);
      }
    }
  }
   while (posIndexFileSeparatorIndexEntries > 0);
}

function $getTranslation(this$static, inputLanguageForSearch, directoryFileLocation, foundAtBeginOfExpression){
  var dictionaryFile, dictionaryFileName, fromText, indexLanguage, toTexts, word;
  dictionaryFileName = '/dictionary/directory' + directoryFileLocation.postfixDictionaryFile + directoryFileLocation.directoryFileNumber + '.csv';
  $logDebug(utilObj_0, 'dictionaryFileName ' + dictionaryFileName);
  $logDebug(utilObj_0, 'position ' + directoryFileLocation.positionInDirectoryFile);
  dictionaryFile = new CsvFile_1(this$static.dictionary_0.dictionaryDataFileISAccess, dictionaryFileName, this$static.dictionary_0.dictionaryFileSeparationCharacter, this$static.dictionary_0.dictionaryCharEncoding, this$static.dictionary_0.dictionaryFileMaxSize, directoryFileLocation.positionInDirectoryFile);
  utilObj_0.logLevel > 1 && $log(utilObj_0, 'dictionaryfile open: : 0');
  fromText = null;
  toTexts = new Vector_0;
  for (indexLanguage = 0; indexLanguage < this$static.dictionary_0.numberOfAvailableLanguages_0; ++indexLanguage) {
    word = $getWord(dictionaryFile);
    $equals('weakCrypt', this$static.dictionary_0.fileEncodingFormat) && weakDecrypt(word);
    if (inputLanguageForSearch == indexLanguage) {
      $convertFieldAndLineSeparatorChars(word);
      fromText = new TextOfLanguage_0(word.impl.toString_0(word.data), indexLanguage, this$static.dictionary_0);
    }
    if (this$static.outputLanguages[indexLanguage]) {
      $convertFieldAndLineSeparatorChars(word);
      $addElement(toTexts, new TextOfLanguage_0(word.impl.toString_0(word.data), indexLanguage, this$static.dictionary_0));
    }
  }
  $addTranslation(this$static, fromText, toTexts, foundAtBeginOfExpression, directoryFileLocation);
}

function $getTranslationResult(this$static){
  var languageCount, languageDefinitionObj, nonNormatedWord, normationObj, searchWord, searchWords, t, toBeTranslatedWordNormated, wordCount;
  this$static.resultOfTranslation = new TranslationResult_0;
  this$static.startTime = fromDouble(currentTimeMillis0());
  utilObj_0.logLevel > 1 && $log(utilObj_0, 'start translation: : 0');
  try {
    for (languageCount = 0; languageCount < this$static.dictionary_0.numberOfAvailableLanguages_0; ++languageCount) {
      languageDefinitionObj = this$static.dictionary_0.supportedLanguages_0[languageCount];
      if (languageDefinitionObj.isSearchable_0 && this$static.inputLanguages[languageCount]) {
        normationObj = this$static.dictionary_0.supportedLanguages_0[languageCount].normationObj_0;
        searchWords = normationObj.searchWord(this$static.toBeTranslatedWordText);
        for (wordCount = 0; wordCount < searchWords.arrayList.size_0; ++wordCount) {
          searchWord = dynamicCast($get_0(searchWords.arrayList, wordCount), Q$SearchedWord);
          nonNormatedWord = searchWord.word;
          toBeTranslatedWordNormated = $toString_1(normationObj.normateWord_0(new StringBuffer_2(nonNormatedWord), true));
          toBeTranslatedWordNormated.length > 0 && $searchTranslationForNormatedWord(this$static, languageCount, toBeTranslatedWordNormated);
        }
      }
    }
  }
   catch ($e0) {
    $e0 = caught($e0);
    if (instanceOf($e0, Q$Throwable)) {
      t = $e0;
      $outputMessage('Thrown ' + $toString(t) + ' / ' + t.getMessage() + '\n');
      $printStackTrace(t);
    }
     else 
      throw $e0;
  }
  fromDouble(currentTimeMillis0());
  utilObj_0.logLevel > 1 && $log(utilObj_0, 'end translation: : 0');
  return this$static.resultOfTranslation;
}

function $noWildcardMatchRest(this$static, searchExpression, indexEntry){
  var indexEntryLength, searchExpressionLength;
  searchExpressionLength = searchExpression.length;
  indexEntryLength = indexEntry.length;
  if (searchExpressionLength == indexEntryLength) {
    return true;
  }
  if (indexEntryLength > searchExpressionLength && this$static.searchSubExpressionEnd) {
    if (indexEntry.charCodeAt(searchExpressionLength) == 32) {
      return true;
    }
  }
  return false;
}

function $searchInIndexFile(this$static, inputLanguageForSearch, toBeTranslatedWordNormated, indexFileNumber){
  var containsWildcard, endOfFirstPartIndexEntry, firstPartIndexEntry, indexEntry, indexEntryLength, indexFile, indexFileName, indexStringLine, initialSearchExpression, initialSearchExpressionLength, languagePostfix;
  $logDebug(utilObj_0, 'indexFileNumber ' + indexFileNumber);
  languagePostfix = this$static.dictionary_0.supportedLanguages_0[inputLanguageForSearch].languageFilePostfix_0;
  indexFileName = '/dictionary/index' + languagePostfix + indexFileNumber + '.csv';
  containsWildcard = positionFirstWildcardCharacter(toBeTranslatedWordNormated) >= 0;
  containsWildcard?(initialSearchExpression = $substring_0(toBeTranslatedWordNormated, 0, positionFirstWildcardCharacter(toBeTranslatedWordNormated))):(initialSearchExpression = toBeTranslatedWordNormated);
  initialSearchExpressionLength = initialSearchExpression.length;
  $logDebug(utilObj_0, 'indexFileName ' + indexFileName);
  indexFile = new CsvFile_0(this$static.dictionary_0.dictionaryDataFileISAccess, indexFileName, this$static.dictionary_0.indexFileSeparationCharacter, this$static.dictionary_0.indexCharEncoding, this$static.dictionary_0.indexFileMaxSize);
  utilObj_0.logLevel > 1 && $log(utilObj_0, 'indexfile open: : 0');
  $setPositionBefore(indexFile, initialSearchExpression);
  while (!indexFile.endOfDictionaryReached) {
    indexEntry = $toString_1($getWord(indexFile));
    if (containsWildcard) {
      endOfFirstPartIndexEntry = initialSearchExpressionLength;
      indexEntryLength = indexEntry.length;
      indexEntryLength < initialSearchExpressionLength && (endOfFirstPartIndexEntry = indexEntryLength);
      firstPartIndexEntry = indexEntry.substr(0, endOfFirstPartIndexEntry - 0);
      if (compareTo(initialSearchExpression, firstPartIndexEntry) < 0) {
        break;
      }
      if ($wildcardMatch(this$static, indexEntry, 0, indexEntry.length, toBeTranslatedWordNormated, 0, toBeTranslatedWordNormated.length)) {
        indexStringLine = $toString_1($getRestOfLine(indexFile));
        $getDictionaryEntry(this$static, inputLanguageForSearch, indexStringLine);
      }
       else {
        $skipRestOfLine(indexFile);
      }
    }
     else {
      if (compareTo(indexEntry, toBeTranslatedWordNormated) < 0) {
        $skipRestOfLine(indexFile);
      }
       else if (indexEntry.indexOf(toBeTranslatedWordNormated) == 0) {
        if ($noWildcardMatchRest(this$static, toBeTranslatedWordNormated, indexEntry)) {
          indexStringLine = $toString_1($getRestOfLine(indexFile));
          $getDictionaryEntry(this$static, inputLanguageForSearch, indexStringLine);
        }
         else {
          $skipRestOfLine(indexFile);
        }
      }
       else {
        break;
      }
    }
  }
}

function $searchInIndexFileBreakCondition(this$static, inputLanguageForSearch, toBeTranslatedWordNormated, indexFileNumber){
  if (this$static.resultOfTranslation.translations.arrayList.size_0 >= this$static.maxHits || gte_0(sub_0(fromDouble(currentTimeMillis0()), this$static.startTime), fromInt(this$static.durationForCancelSearch)) || this$static.translationIsCancelled) {
    return true;
  }
   else {
    $searchInIndexFile(this$static, inputLanguageForSearch, toBeTranslatedWordNormated, indexFileNumber);
    return this$static.resultOfTranslation.translations.arrayList.size_0 >= this$static.maxHits || gte_0(sub_0(fromDouble(currentTimeMillis0()), this$static.startTime), fromInt(this$static.durationForCancelSearch)) || this$static.translationIsCancelled;
  }
}

function $searchTranslationForNormatedWord(this$static, inputLanguageForSearch, toBeTranslatedWordNormated){
  var containsWildcard, continueSearch, endOfFirstPartWordInIndex, firstPartWordInIndex, indexFileNumber, initialSearchExpression, initialSearchExpressionLength, languagePostfix, lastIndexFileSearched, searchListFile, searchListFileName, wordInIndex, wordInIndexLength;
  containsWildcard = positionFirstWildcardCharacter(toBeTranslatedWordNormated) >= 0;
  containsWildcard?(initialSearchExpression = $substring_0(toBeTranslatedWordNormated, 0, positionFirstWildcardCharacter(toBeTranslatedWordNormated))):(initialSearchExpression = toBeTranslatedWordNormated);
  initialSearchExpressionLength = initialSearchExpression.length;
  languagePostfix = this$static.dictionary_0.supportedLanguages_0[inputLanguageForSearch].languageFilePostfix_0;
  searchListFileName = '/dictionary/searchlist' + languagePostfix + '.csv';
  searchListFile = new CsvFile_0(this$static.dictionary_0.dictionaryDataFileISAccess, searchListFileName, this$static.dictionary_0.searchListFileSeparationCharacter, this$static.dictionary_0.searchListCharEncoding, this$static.dictionary_0.searchListFileMaxSize);
  utilObj_0.logLevel > 1 && $log(utilObj_0, 'searchfile open: : 0');
  indexFileNumber = null;
  lastIndexFileSearched = false;
  $setPositionBefore(searchListFile, initialSearchExpression);
  while (!searchListFile.endOfDictionaryReached) {
    wordInIndex = $toString_1($getWord(searchListFile));
    if (containsWildcard) {
      endOfFirstPartWordInIndex = initialSearchExpressionLength;
      wordInIndexLength = wordInIndex.length;
      wordInIndexLength < initialSearchExpressionLength && (endOfFirstPartWordInIndex = wordInIndexLength);
      firstPartWordInIndex = wordInIndex.substr(0, endOfFirstPartWordInIndex - 0);
      if (firstPartWordInIndex.indexOf(initialSearchExpression) == 0) {
        if (!lastIndexFileSearched) {
          if (indexFileNumber != null) {
            if ($searchInIndexFileBreakCondition(this$static, inputLanguageForSearch, toBeTranslatedWordNormated, indexFileNumber))
              break;
          }
        }
        indexFileNumber = $toString_1($getWord(searchListFile));
        if ($searchInIndexFileBreakCondition(this$static, inputLanguageForSearch, toBeTranslatedWordNormated, indexFileNumber))
          break;
        lastIndexFileSearched = true;
      }
       else if (compareTo(firstPartWordInIndex, initialSearchExpression) > 0) {
        if (!lastIndexFileSearched)
          if (indexFileNumber != null) {
            if ($searchInIndexFileBreakCondition(this$static, inputLanguageForSearch, toBeTranslatedWordNormated, indexFileNumber))
              break;
          }
        break;
      }
       else {
        indexFileNumber = $toString_1($getWord(searchListFile));
      }
    }
     else {
      if (compareTo(wordInIndex, initialSearchExpression) >= 0) {
        if (indexFileNumber != null) {
          if ($searchInIndexFileBreakCondition(this$static, inputLanguageForSearch, toBeTranslatedWordNormated, indexFileNumber))
            break;
        }
        continueSearch = false;
        wordInIndex.indexOf(initialSearchExpression) == 0 && (wordInIndex.length > initialSearchExpression.length?$charAt(wordInIndex, initialSearchExpression.length) == 32 && (continueSearch = true):(continueSearch = true));
        if (continueSearch) {
          indexFileNumber = $toString_1($getWord(searchListFile));
        }
         else {
          break;
        }
      }
       else {
        indexFileNumber = $toString_1($getWord(searchListFile));
      }
    }
  }
  searchListFile.endOfDictionaryReached && !lastIndexFileSearched && $searchInIndexFileBreakCondition(this$static, inputLanguageForSearch, toBeTranslatedWordNormated, indexFileNumber);
}

function $wildcardMatch(this$static, toBeSearchedExpression, positionCharacterToBeSearchedExpression, lengthToBeSearchedExpression, wildcardExpression, positionCharacterWildcardExpression, lengthWildcardExpression){
  var characterToBeSearchedExpression, characterWildcardExpression, expressionsMatch, position, restIsAnySeriesOfCharacter;
  expressionsMatch = false;
  if (positionCharacterWildcardExpression == lengthWildcardExpression) {
    positionCharacterToBeSearchedExpression == lengthToBeSearchedExpression?(expressionsMatch = true):lengthToBeSearchedExpression > positionCharacterToBeSearchedExpression && this$static.searchSubExpressionEnd?toBeSearchedExpression.charCodeAt(positionCharacterToBeSearchedExpression) == 32?(expressionsMatch = true):(expressionsMatch = false):(expressionsMatch = false);
  }
   else if (positionCharacterToBeSearchedExpression == lengthToBeSearchedExpression) {
    restIsAnySeriesOfCharacter = true;
    for (position = positionCharacterWildcardExpression; position < lengthWildcardExpression; ++position) {
      if (wildcardExpression.charCodeAt(position) != 42) {
        restIsAnySeriesOfCharacter = false;
        break;
      }
    }
    expressionsMatch = restIsAnySeriesOfCharacter;
  }
   else {
    characterToBeSearchedExpression = toBeSearchedExpression.charCodeAt(positionCharacterToBeSearchedExpression);
    characterWildcardExpression = wildcardExpression.charCodeAt(positionCharacterWildcardExpression);
    if (characterWildcardExpression == 63) {
      expressionsMatch = $wildcardMatch(this$static, toBeSearchedExpression, positionCharacterToBeSearchedExpression + 1, lengthToBeSearchedExpression, wildcardExpression, positionCharacterWildcardExpression + 1, lengthWildcardExpression);
    }
     else if (characterWildcardExpression == 42) {
      for (position = positionCharacterToBeSearchedExpression; position <= lengthToBeSearchedExpression; ++position) {
        expressionsMatch = $wildcardMatch(this$static, toBeSearchedExpression, position, lengthToBeSearchedExpression, wildcardExpression, positionCharacterWildcardExpression + 1, lengthWildcardExpression);
        if (expressionsMatch)
          break;
      }
    }
     else 
      characterWildcardExpression == characterToBeSearchedExpression && (expressionsMatch = $wildcardMatch(this$static, toBeSearchedExpression, positionCharacterToBeSearchedExpression + 1, lengthToBeSearchedExpression, wildcardExpression, positionCharacterWildcardExpression + 1, lengthWildcardExpression));
  }
  return expressionsMatch;
}

function Translation_0(translationParametersObj_0){
  $clinit_Translation();
  this.dictionary_0 = translationParametersObj_0.dictionary_0;
  this.toBeTranslatedWordText = translationParametersObj_0.toBeTranslatedWordText;
  this.inputLanguages = translationParametersObj_0.inputLanguages;
  this.outputLanguages = translationParametersObj_0.outputLanguages;
  this.searchSubExpressionStart = translationParametersObj_0.searchSubExpressionStart;
  this.searchSubExpressionEnd = translationParametersObj_0.searchSubExpressionEnd;
  this.maxHits = translationParametersObj_0.maxHits;
  this.durationForCancelSearch = translationParametersObj_0.durationForCancelSearch;
  if (this.inputLanguages.length != this.dictionary_0.numberOfAvailableLanguages_0 || this.outputLanguages.length != this.dictionary_0.numberOfAvailableLanguages_0) {
    throw new DictionaryException_0('Incorrect number of array elements for inputLanguages/outputLanguages');
  }
}

function positionFirstWildcardCharacter(word){
  var index, indexWildcardAnySeriesOfCharacters, indexWildcardAnySingleCharacter;
  indexWildcardAnySeriesOfCharacters = $indexOf(word, fromCodePoint(42));
  indexWildcardAnySingleCharacter = $indexOf(word, fromCodePoint(63));
  indexWildcardAnySingleCharacter == -1 && indexWildcardAnySeriesOfCharacters == -1?(index = -1):indexWildcardAnySingleCharacter == -1 && indexWildcardAnySeriesOfCharacters > -1?(index = indexWildcardAnySeriesOfCharacters):indexWildcardAnySingleCharacter > -1 && indexWildcardAnySeriesOfCharacters == -1?(index = indexWildcardAnySingleCharacter):indexWildcardAnySingleCharacter < indexWildcardAnySeriesOfCharacters?(index = indexWildcardAnySingleCharacter):(index = indexWildcardAnySeriesOfCharacters);
  return index;
}

function weakDecrypt(word){
  var ch, i, limit, n;
  n = 0;
  limit = word.impl.length_0(word.data);
  for (i = 0; i < limit; ++i) {
    ch = $charAt(word.impl.toString_0(word.data), i);
    if (ch >= 60 && ch < 124) {
      ch = ((ch - 60 ^ 43) - weakEncrypt_password[n] + 256) % 64 + 60 & 65535;
      $replace(word, i, i + 1, String.fromCharCode(ch));
      n = (n + 1) % weakEncrypt_password.length;
    }
     else {
      n = 0;
    }
  }
}

defineSeed(91, 1, {}, Translation_0);
_.dictionary_0 = null;
_.durationForCancelSearch = 0;
_.inputLanguages = null;
_.maxHits = 0;
_.outputLanguages = null;
_.resultOfTranslation = null;
_.searchSubExpressionEnd = false;
_.searchSubExpressionStart = false;
_.startTime = P0_longLit;
_.toBeTranslatedWordText = null;
_.translationIsCancelled = false;
var weakEncrypt_password;
function $clinit_TranslationExecution(){
  $clinit_TranslationExecution = nullMethod;
  lastTranslationThreads = new Vector_0;
  accessToHMIObj = new TranslationThreadCallback_0;
}

function cancelLastTranslation(){
  var lastTranslationThreadToBeCancelled, lastTranslationThreadsEnum;
  lastTranslationThreadsEnum = enumeration(lastTranslationThreads.arrayList);
  while ($hasNext(lastTranslationThreadsEnum.val$it)) {
    lastTranslationThreadToBeCancelled = dynamicCast($next(lastTranslationThreadsEnum.val$it), Q$TranslationThreadIF);
    lastTranslationThreadToBeCancelled.translationCancelledIndication = true;
    !!lastTranslationThreadToBeCancelled.translate && (lastTranslationThreadToBeCancelled.translate.translationIsCancelled = true);
  }
  $clear(lastTranslationThreads.arrayList);
}

function executeTranslationBatch_0(translationParametersBatchObj){
  $clinit_TranslationExecution();
  var allTranslationParameters, newTranslationThread, translationParametersObj_0;
  cancelLastTranslation();
  accessToHMIObj.noTranslationDoneYet = true;
  allTranslationParameters = enumeration(translationParametersBatchObj.translationParametersVector.arrayList);
  while ($hasNext(allTranslationParameters.val$it)) {
    translationParametersObj_0 = dynamicCast($next(allTranslationParameters.val$it), Q$TranslationParameters);
    newTranslationThread = new TranslationThreadJava_0(accessToHMIObj, translationParametersObj_0);
    $add_1(lastTranslationThreads, newTranslationThread);
    newTranslationThread.translationParametersObj.executeInBackground?null.nullMethod():$doTranslation(newTranslationThread);
  }
}

var accessToHMIObj, lastTranslationThreads;
function TranslationParameters_0(dictionaryParam, toBeTranslatedWordTextInputParam, inputLanguagesParam, outputLanguagesParam, executeInBackgroundParam, maxHitsParam, durationForCancelSearchParam){
  this.dictionary_0 = dictionaryParam;
  this.toBeTranslatedWordText = toBeTranslatedWordTextInputParam;
  this.inputLanguages = inputLanguagesParam;
  this.outputLanguages = outputLanguagesParam;
  this.executeInBackground = executeInBackgroundParam;
  this.maxHits = maxHitsParam;
  this.durationForCancelSearch = durationForCancelSearchParam;
  this.toBeTranslatedWordText = removeSuperflousSearchCharacters(this.toBeTranslatedWordText);
  this.searchSubExpressionStart = true;
  this.searchSubExpressionEnd = true;
  if (this.toBeTranslatedWordText.length > 0 && this.toBeTranslatedWordText.charCodeAt(0) == 47) {
    this.searchSubExpressionStart = false;
    this.toBeTranslatedWordText.length > 1?(this.toBeTranslatedWordText = $substring(this.toBeTranslatedWordText, 1)):(this.toBeTranslatedWordText = '');
  }
   else {
    this.searchSubExpressionStart = true;
  }
  if (this.toBeTranslatedWordText.length > 0 && $charAt(this.toBeTranslatedWordText, this.toBeTranslatedWordText.length - 1) == 47) {
    this.searchSubExpressionEnd = false;
    this.toBeTranslatedWordText = $substring_0(this.toBeTranslatedWordText, 0, this.toBeTranslatedWordText.length - 1);
  }
   else {
    this.searchSubExpressionEnd = true;
  }
}

defineSeed(93, 1, makeCastMap([Q$TranslationParameters]), TranslationParameters_0);
_.getDictionary_0 = function getDictionary(){
  return this.dictionary_0;
}
;
_.getDurationForCancelSearch_0 = function getDurationForCancelSearch(){
  return this.durationForCancelSearch;
}
;
_.getInputLanguages_0 = function getInputLanguages(){
  return this.inputLanguages;
}
;
_.getMaxHits_0 = function getMaxHits(){
  return this.maxHits;
}
;
_.getOutputLanguages_0 = function getOutputLanguages(){
  return this.outputLanguages;
}
;
_.getToBeTranslatedWordText_0 = function getToBeTranslatedWordText(){
  return this.toBeTranslatedWordText;
}
;
_.dictionary_0 = null;
_.durationForCancelSearch = 0;
_.executeInBackground = false;
_.inputLanguages = null;
_.maxHits = 0;
_.outputLanguages = null;
_.searchSubExpressionEnd = false;
_.searchSubExpressionStart = false;
_.toBeTranslatedWordText = null;
function TranslationParametersBatch_0(){
  this.translationParametersVector = new Vector_0;
}

defineSeed(94, 1, {}, TranslationParametersBatch_0);
_.addTranslationParameters_0 = function addTranslationParameters(translationParametersObj_0){
  $add_1(this.translationParametersVector, translationParametersObj_0);
}
;
_.getAllTranslationParameters_0 = function getAllTranslationParameters(){
  return enumeration(this.translationParametersVector.arrayList);
}
;
_.getTranslationParametersAt_0 = function getTranslationParametersAt(index){
  return dynamicCast($get_1(this.translationParametersVector, index), Q$TranslationParameters);
}
;
_.insertTranslationParametersAt_0 = function insertTranslationParametersAt(translationParametersObj_0, index){
  $insertElementAt(this.translationParametersVector, translationParametersObj_0, index);
}
;
_.numberOfTranslationParameters_0 = function numberOfTranslationParameters(){
  return this.translationParametersVector.arrayList.size_0;
}
;
_.removeAllTranslationParameters_0 = function removeAllTranslationParameters(){
  $clear(this.translationParametersVector.arrayList);
}
;
_.removeTranslationParametersAt_0 = function removeTranslationParametersAt(index){
  $remove_0(this.translationParametersVector, index);
}
;
function $addTranslation_0(this$static, newSingleTranslation){
  $addElement(this$static.translations, newSingleTranslation);
}

function $insertTranslationAt(this$static, newSingleTranslation, index){
  $insertElementAt(this$static.translations, newSingleTranslation, index);
}

function TranslationResult_0(){
  this.translations = new Vector_0;
}

defineSeed(95, 1, {}, TranslationResult_0);
_.getTranslationAt_0 = function getTranslationAt(index){
  return dynamicCast($elementAt(this.translations, index), Q$SingleTranslation);
}
;
_.numberOfFoundTranslations_0 = function numberOfFoundTranslations(){
  return this.translations.arrayList.size_0;
}
;
_.translationFound_0 = function translationFound(){
  return this.translations.arrayList.size_0 > 0;
}
;
function $setTranslationExecutionCallback(this$static, translationResultHMIObjParam){
  this$static.translationResultHMIObj = translationResultHMIObjParam;
}

function $translationDone(this$static, resultOfTranslation, callingThread){
  if (!callingThread.translationCancelledIndication) {
    if (!this$static.translationResultHMIObj) {
      throw new DictionaryException_0('No callback for translation results set. Call TranslationExecution.setTranslationExecutionCallback.');
    }
    if (this$static.noTranslationDoneYet) {
      TranslationExecution.deletePreviousTranslationResultCallback();
      this$static.noTranslationDoneYet = false;
    }
    callNewTranslationResultJs(resultOfTranslation);
  }
}

function TranslationThreadCallback_0(){
}

defineSeed(96, 1, {}, TranslationThreadCallback_0);
_.noTranslationDoneYet = false;
_.translationResultHMIObj = null;
defineSeed(98, 1, {});
function $doTranslation(this$static){
  var resultOfTranslation;
  this$static.translationParametersObj.executeInBackground && undefined;
  if (this$static.translationParametersObj.toBeTranslatedWordText.length != 0) {
    this$static.translate = new Translation_0(this$static.translationParametersObj);
    resultOfTranslation = null;
    this$static.translationCancelledIndication || (resultOfTranslation = $getTranslationResult(this$static.translate));
    this$static.translationCancelledIndication || $translationDone(this$static.translationThreadCallbackObj, resultOfTranslation, this$static);
  }
}

function TranslationThreadJava_0(translationThreadCallbackParam, translationParametersObjParam){
  this.translationThreadCallbackObj = translationThreadCallbackParam;
  this.translationParametersObj = translationParametersObjParam;
}

defineSeed(97, 98, makeCastMap([Q$TranslationThreadIF]), TranslationThreadJava_0);
_.translate = null;
_.translationCancelledIndication = false;
_.translationParametersObj = null;
_.translationThreadCallbackObj = null;
function Normation_0(){
}

defineSeed(99, 1, makeCastMap([Q$Normation]), Normation_0);
_.normateWord = function normateWord(nonNormatedWord){
  return nonNormatedWord;
}
;
_.normateWord_0 = function normateWord_0(nonNormatedWord, fromUserInput){
  return this.normateWord(nonNormatedWord);
}
;
_.searchWord = function searchWord_0(text){
  var words;
  words = new Vector_0;
  $addElement(words, new SearchedWord_0(text));
  return words;
}
;
_.dictionaryDataFileISAccess = null;
function NormationBul_0(){
}

defineSeed(100, 99, makeCastMap([Q$Normation]), NormationBul_0);
_.normateWord_0 = function normateWord_1(nonNormatedWord, fromUserInput){
  var charPos, defaultNormatedWord, normatedWord;
  defaultNormatedWord = defaultNormation(nonNormatedWord, fromUserInput);
  normatedWord = new StringBuffer_0;
  for (charPos = 0; charPos < defaultNormatedWord.impl.length_0(defaultNormatedWord.data); ++charPos) {
    $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1072?(normatedWord.impl.append(normatedWord.data, 'a') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1073?(normatedWord.impl.append(normatedWord.data, 'b') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1074?(normatedWord.impl.append(normatedWord.data, 'v') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1075?(normatedWord.impl.append(normatedWord.data, 'g') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1076?(normatedWord.impl.append(normatedWord.data, 'd') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1077?(normatedWord.impl.append(normatedWord.data, 'e') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1078?(normatedWord.impl.append(normatedWord.data, 'zh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1079?(normatedWord.impl.append(normatedWord.data, 'z') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1080?(normatedWord.impl.append(normatedWord.data, 'i') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1081?(normatedWord.impl.append(normatedWord.data, 'y') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1082?(normatedWord.impl.append(normatedWord.data, 'k') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1083?(normatedWord.impl.append(normatedWord.data, 'l') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1084?(normatedWord.impl.append(normatedWord.data, 'm') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1085?(normatedWord.impl.append(normatedWord.data, 'n') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1086?(normatedWord.impl.append(normatedWord.data, 'o') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1087?(normatedWord.impl.append(normatedWord.data, 'p') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1088?(normatedWord.impl.append(normatedWord.data, 'r') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1089?(normatedWord.impl.append(normatedWord.data, 's') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1090?(normatedWord.impl.append(normatedWord.data, 't') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1091?(normatedWord.impl.append(normatedWord.data, 'u') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1092?(normatedWord.impl.append(normatedWord.data, 'f') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1093?(normatedWord.impl.append(normatedWord.data, 'h') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1094?(normatedWord.impl.append(normatedWord.data, 'ts') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1095?(normatedWord.impl.append(normatedWord.data, 'ch') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1096?(normatedWord.impl.append(normatedWord.data, 'sh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1097?(normatedWord.impl.append(normatedWord.data, 'sht') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1098?(normatedWord.impl.append(normatedWord.data, 'aj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1100?(normatedWord.impl.append(normatedWord.data, 'x') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1102?(normatedWord.impl.append(normatedWord.data, 'yu') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1103?(normatedWord.impl.append(normatedWord.data, 'ya') , normatedWord):$append(normatedWord, $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos));
  }
  return normatedWord;
}
;
function NormationCyr1_0(){
}

defineSeed(101, 99, makeCastMap([Q$Normation]), NormationCyr1_0);
_.normateWord_0 = function normateWord_2(nonNormatedWord, fromUserInput){
  var charPos, defaultNormatedWord, normatedWord;
  defaultNormatedWord = defaultNormation(nonNormatedWord, fromUserInput);
  normatedWord = new StringBuffer_0;
  for (charPos = 0; charPos < defaultNormatedWord.impl.length_0(defaultNormatedWord.data); ++charPos) {
    $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1072?(normatedWord.impl.append(normatedWord.data, 'a') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1073?(normatedWord.impl.append(normatedWord.data, 'b') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1074?(normatedWord.impl.append(normatedWord.data, 'v') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1075?(normatedWord.impl.append(normatedWord.data, 'g') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1076?(normatedWord.impl.append(normatedWord.data, 'd') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1077?(normatedWord.impl.append(normatedWord.data, 'e') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1105?(normatedWord.impl.append(normatedWord.data, 'jo') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1078?(normatedWord.impl.append(normatedWord.data, 'zh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1079?(normatedWord.impl.append(normatedWord.data, 'z') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1080?(normatedWord.impl.append(normatedWord.data, 'i') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1081?(normatedWord.impl.append(normatedWord.data, 'j') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1082?(normatedWord.impl.append(normatedWord.data, 'k') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1083?(normatedWord.impl.append(normatedWord.data, 'l') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1084?(normatedWord.impl.append(normatedWord.data, 'm') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1085?(normatedWord.impl.append(normatedWord.data, 'n') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1086?(normatedWord.impl.append(normatedWord.data, 'o') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1087?(normatedWord.impl.append(normatedWord.data, 'p') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1088?(normatedWord.impl.append(normatedWord.data, 'r') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1089?(normatedWord.impl.append(normatedWord.data, 's') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1090?(normatedWord.impl.append(normatedWord.data, 't') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1091?(normatedWord.impl.append(normatedWord.data, 'u') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1092?(normatedWord.impl.append(normatedWord.data, 'f') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1093?(normatedWord.impl.append(normatedWord.data, 'h') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1094?(normatedWord.impl.append(normatedWord.data, 'c') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1095?(normatedWord.impl.append(normatedWord.data, 'ch') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1096?(normatedWord.impl.append(normatedWord.data, 'sh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1097?(normatedWord.impl.append(normatedWord.data, 'shh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1099?(normatedWord.impl.append(normatedWord.data, 'y') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1100?(normatedWord.impl.append(normatedWord.data, 'x') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1101?(normatedWord.impl.append(normatedWord.data, 'eh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1102?(normatedWord.impl.append(normatedWord.data, 'ju') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1103?(normatedWord.impl.append(normatedWord.data, 'ja') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1110?(normatedWord.impl.append(normatedWord.data, 'ij') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1169?(normatedWord.impl.append(normatedWord.data, 'gj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1107?(normatedWord.impl.append(normatedWord.data, 'gj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1108?(normatedWord.impl.append(normatedWord.data, 'ye') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1111?(normatedWord.impl.append(normatedWord.data, 'yi') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1109?(normatedWord.impl.append(normatedWord.data, 'dz') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1112?(normatedWord.impl.append(normatedWord.data, 'jj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1113?(normatedWord.impl.append(normatedWord.data, 'lj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1114?(normatedWord.impl.append(normatedWord.data, 'nj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1116?(normatedWord.impl.append(normatedWord.data, 'kj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1119?(normatedWord.impl.append(normatedWord.data, 'dj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1118?(normatedWord.impl.append(normatedWord.data, 'uj') , normatedWord):$append(normatedWord, $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos));
  }
  return normatedWord;
}
;
function NormationCyr2_0(){
}

defineSeed(102, 99, makeCastMap([Q$Normation]), NormationCyr2_0);
_.normateWord_0 = function normateWord_3(nonNormatedWord, fromUserInput){
  var charPos, defaultNormatedWord, normatedWord;
  defaultNormatedWord = defaultNormation(nonNormatedWord, fromUserInput);
  normatedWord = new StringBuffer_0;
  for (charPos = 0; charPos < defaultNormatedWord.impl.length_0(defaultNormatedWord.data); ++charPos) {
    $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1072?(normatedWord.impl.append(normatedWord.data, 'a') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1073?(normatedWord.impl.append(normatedWord.data, 'b') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1074?(normatedWord.impl.append(normatedWord.data, 'v') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1075?(normatedWord.impl.append(normatedWord.data, 'g') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1076?(normatedWord.impl.append(normatedWord.data, 'd') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1077?(normatedWord.impl.append(normatedWord.data, 'e') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1105?(normatedWord.impl.append(normatedWord.data, 'yo') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1078?(normatedWord.impl.append(normatedWord.data, 'zh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1079?(normatedWord.impl.append(normatedWord.data, 'z') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1080?(normatedWord.impl.append(normatedWord.data, 'i') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1081?(normatedWord.impl.append(normatedWord.data, 'j') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1082?(normatedWord.impl.append(normatedWord.data, 'k') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1083?(normatedWord.impl.append(normatedWord.data, 'l') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1084?(normatedWord.impl.append(normatedWord.data, 'm') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1085?(normatedWord.impl.append(normatedWord.data, 'n') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1086?(normatedWord.impl.append(normatedWord.data, 'o') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1087?(normatedWord.impl.append(normatedWord.data, 'p') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1088?(normatedWord.impl.append(normatedWord.data, 'r') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1089?(normatedWord.impl.append(normatedWord.data, 's') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1090?(normatedWord.impl.append(normatedWord.data, 't') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1091?(normatedWord.impl.append(normatedWord.data, 'u') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1092?(normatedWord.impl.append(normatedWord.data, 'f') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1093?(normatedWord.impl.append(normatedWord.data, 'h') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1094?(normatedWord.impl.append(normatedWord.data, 'c') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1095?(normatedWord.impl.append(normatedWord.data, 'ch') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1096?(normatedWord.impl.append(normatedWord.data, 'sh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1097?(normatedWord.impl.append(normatedWord.data, 'shh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1098?(normatedWord.impl.append(normatedWord.data, 'aj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1099?(normatedWord.impl.append(normatedWord.data, 'y') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1100?(normatedWord.impl.append(normatedWord.data, 'x') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1101?(normatedWord.impl.append(normatedWord.data, 'eh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1102?(normatedWord.impl.append(normatedWord.data, 'yu') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1103?(normatedWord.impl.append(normatedWord.data, 'ya') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1110?(normatedWord.impl.append(normatedWord.data, 'ij') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1139?(normatedWord.impl.append(normatedWord.data, 'fh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1123?(normatedWord.impl.append(normatedWord.data, 'je') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1141?(normatedWord.impl.append(normatedWord.data, 'yh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1169?(normatedWord.impl.append(normatedWord.data, 'gj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1107?(normatedWord.impl.append(normatedWord.data, 'gj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1108?(normatedWord.impl.append(normatedWord.data, 'ye') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1111?(normatedWord.impl.append(normatedWord.data, 'yi') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1109?(normatedWord.impl.append(normatedWord.data, 'dz') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1112?(normatedWord.impl.append(normatedWord.data, 'jj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1113?(normatedWord.impl.append(normatedWord.data, 'lj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1114?(normatedWord.impl.append(normatedWord.data, 'nj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1116?(normatedWord.impl.append(normatedWord.data, 'kj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1119?(normatedWord.impl.append(normatedWord.data, 'dj') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1118?(normatedWord.impl.append(normatedWord.data, 'uj') , normatedWord):$append(normatedWord, $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos));
  }
  return normatedWord;
}
;
function NormationEng_0(){
}

defineSeed(103, 99, makeCastMap([Q$Normation]), NormationEng_0);
_.normateWord_0 = function normateWord_4(nonNormatedWord, fromUserInput){
  var defaultNormatedWord;
  defaultNormatedWord = defaultNormation(nonNormatedWord, fromUserInput);
  return defaultNormatedWord;
}
;
function NormationEng2_0(){
}

defineSeed(104, 99, makeCastMap([Q$Normation]), NormationEng2_0);
_.normateWord = function normateWord_5(nonNormatedWord){
  var ch, charPos, lastChar, normatedWord, normatedWordLength, strLength;
  normatedWord = new StringBuffer_0;
  strLength = nonNormatedWord.impl.length_0(nonNormatedWord.data);
  lastChar = 32;
  normatedWordLength = 0;
  for (charPos = 0; charPos < strLength; ++charPos) {
    ch = $charAt(nonNormatedWord.impl.toString_0(nonNormatedWord.data), charPos);
    if ($indexOf('\xE0\xE1\xE2\xC0\xC1\xC2\xF9\xFA\xD9\xDA\xF2\xF3\xF4\xF5\xD2\xD3\xD4\xD5\xE8\xE9\xEA\xC8\xC9\xCA\xEF\xCF\xF1\xD1\xE7\xC7', fromCodePoint(ch)) != -1) {
      $append(normatedWord, $charAt('aaaaaauuuuooooooooeeeeeeiinncc', $indexOf('\xE0\xE1\xE2\xC0\xC1\xC2\xF9\xFA\xD9\xDA\xF2\xF3\xF4\xF5\xD2\xD3\xD4\xD5\xE8\xE9\xEA\xC8\xC9\xCA\xEF\xCF\xF1\xD1\xE7\xC7', fromCodePoint(ch))));
      ++normatedWordLength;
    }
     else if ($indexOf("'!\xA7$%&/(){}[]=;:,.+-#~@|<>\\ ", fromCodePoint(ch)) != -1) {
      if (lastChar != 32 && normatedWordLength > 0) {
        normatedWord.impl.appendNonNull(normatedWord.data, ' ');
        ++normatedWordLength;
      }
      ch = 32;
    }
     else {
      $append(normatedWord, toLowerCase(ch));
      ++normatedWordLength;
    }
    lastChar = ch;
  }
  lastChar == 32 && normatedWordLength > 0 && $setLength(normatedWord, normatedWordLength - 1);
  return normatedWord;
}
;
function NormationEpo_0(){
}

defineSeed(105, 99, makeCastMap([Q$Normation]), NormationEpo_0);
_.normateWord_0 = function normateWord_6(nonNormatedWord, fromUserInput){
  var charPos, character, defaultNormatedWord, normatedWord;
  defaultNormatedWord = defaultNormation(nonNormatedWord, fromUserInput);
  normatedWord = new StringBuffer_0;
  for (charPos = 0; charPos < defaultNormatedWord.impl.length_0(defaultNormatedWord.data); ++charPos) {
    character = $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos);
    switch (character) {
      case 265:
      case 264:
        normatedWord.impl.append(normatedWord.data, 'c');
        break;
      case 285:
      case 284:
        normatedWord.impl.append(normatedWord.data, 'g');
        break;
      case 293:
      case 292:
        normatedWord.impl.append(normatedWord.data, 'h');
        break;
      case 309:
      case 308:
        normatedWord.impl.append(normatedWord.data, 'j');
        break;
      case 349:
      case 348:
        normatedWord.impl.append(normatedWord.data, 's');
        break;
      case 365:
      case 364:
        normatedWord.impl.append(normatedWord.data, 'u');
        break;
      case 120:
      case 88:
        break;
      default:normatedWord.impl.appendNonNull(normatedWord.data, String.fromCharCode(character));
    }
  }
  return normatedWord;
}
;
function NormationFil_0(){
}

defineSeed(106, 99, makeCastMap([Q$Normation]), NormationFil_0);
_.normateWord_0 = function normateWord_7(nonNormatedWord, fromUserInput){
  var charPos, defaultNormatedWord, normatedWord;
  defaultNormatedWord = $normateWord((!this.normationLatObj && (this.normationLatObj = new NormationLat_0) , nonNormatedWord), fromUserInput);
  normatedWord = new StringBuffer_0;
  for (charPos = 0; charPos < defaultNormatedWord.impl.length_0(defaultNormatedWord.data); ++charPos) {
    $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 98?(normatedWord.impl.append(normatedWord.data, 'v') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 105?(normatedWord.impl.append(normatedWord.data, 'e') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 111?(normatedWord.impl.append(normatedWord.data, 'u') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 102?(normatedWord.impl.append(normatedWord.data, 'p') , normatedWord):$append(normatedWord, $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos));
  }
  return normatedWord;
}
;
_.normationLatObj = null;
function NormationGer_0(){
}

defineSeed(107, 99, makeCastMap([Q$Normation]), NormationGer_0);
_.normateWord_0 = function normateWord_8(nonNormatedWord, fromUserInput){
  var charPos, defaultNormatedWord, normatedWord;
  defaultNormatedWord = defaultNormation(nonNormatedWord, fromUserInput);
  normatedWord = new StringBuffer_0;
  for (charPos = 0; charPos < defaultNormatedWord.impl.length_0(defaultNormatedWord.data); ++charPos) {
    $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 228?(normatedWord.impl.append(normatedWord.data, 'ae') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 246?(normatedWord.impl.append(normatedWord.data, 'oe') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 252?(normatedWord.impl.append(normatedWord.data, 'ue') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 223?(normatedWord.impl.append(normatedWord.data, 'ss') , normatedWord):$append(normatedWord, $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos));
  }
  return normatedWord;
}
;
function $isHiragana(c){
  if (c >= 12352 && c <= 12447)
    return true;
  return false;
}

function $isKatakana(c){
  if (c >= 12448 && c <= 12543)
    return true;
  return false;
}

function $isRomaji(c){
  if (c >= 97 && c <= 122 || c >= 65 && c <= 90)
    return true;
  return false;
}

function NormationJpn_0(){
  this.parserObj = new Parser_0;
}

defineSeed(108, 99, makeCastMap([Q$Normation]), NormationJpn_0);
_.searchWord = function searchWord_1(text){
  var hiraganaWord, i, katakanaWord, romajiWord, sub, words;
  $setDictionaryDataFileISAccess_0(this.parserObj, this.dictionaryDataFileISAccess);
  words = new Vector_1;
  text = $toString_1(convertToLowerCase(new StringBuffer_2(text)));
  i = 0;
  sub = text.charCodeAt(0);
  while (sub == 42 || sub == 63)
    sub = $charAt(text, ++i);
  if ($isRomaji(sub)) {
    hiraganaWord = $convert(this.parserObj, text, 0, true);
    $addElement(words, new SearchedWord_0(hiraganaWord));
    katakanaWord = $convert(this.parserObj, text, 1, true);
    $addElement(words, new SearchedWord_0(katakanaWord));
  }
   else if ($isHiragana(sub)) {
    $addElement(words, new SearchedWord_0(text));
    romajiWord = $convert(this.parserObj, text, 0, false);
    katakanaWord = $convert(this.parserObj, romajiWord, 1, true);
    $addElement(words, new SearchedWord_0(katakanaWord));
  }
   else if ($isKatakana(sub)) {
    $addElement(words, new SearchedWord_0(text));
    romajiWord = $convert(this.parserObj, text, 1, false);
    hiraganaWord = $convert(this.parserObj, romajiWord, 0, true);
    $addElement(words, new SearchedWord_0(hiraganaWord));
  }
  return words;
}
;
_.parserObj = null;
function $normateWord(nonNormatedWord, fromUserInput){
  var charPos, character, defaultNormatedWord, normatedWord;
  defaultNormatedWord = defaultNormation(nonNormatedWord, fromUserInput);
  normatedWord = new StringBuffer_0;
  for (charPos = 0; charPos < defaultNormatedWord.impl.length_0(defaultNormatedWord.data); ++charPos) {
    character = $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos);
    $indexOf('\xE0\xE1\xE2\xE3\xE4\xE5\xE6\u0101\u0103\u0105', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'a') , normatedWord):$indexOf('\xF9\xFA\xFB\xFC\u0169\u016B\u016D\u016F\u0171\u0173', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'u') , normatedWord):$indexOf('\xF2\xF3\xF4\xF5\xF6\xF8\u014D\u014F', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'o') , normatedWord):$indexOf('\xE7\u0107\u0109\u010B\u010D', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'c') , normatedWord):$indexOf('\xE8\xE9\xEA\xEB\u0113\u0115\u0117\u0119\u011B\xE6\u0153', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'e') , normatedWord):$indexOf('\xEC\xED\xEE\xEF\u0129\u012B\u012D\u012F\u0131\u0133', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'i') , normatedWord):$indexOf('\xF1\u0148\u0144\u0146', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'n') , normatedWord):$indexOf('\u010F\u0111\xF0', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'd') , normatedWord):$indexOf('\u011F\u0123\u0121', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'g') , normatedWord):$indexOf('\u0127', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'h') , normatedWord):$indexOf('\u0137', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'k') , normatedWord):$indexOf('\u013A\u013E\u0142\u013C', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'l') , normatedWord):$indexOf('\u0155\u0159\u0157', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'r') , normatedWord):$indexOf('\u0161\u015B\u015F\u0219', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 's') , normatedWord):$indexOf('\u0165\u0163\u021B\xFE', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 't') , normatedWord):$indexOf('\xFD\xFF', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'y') , normatedWord):$indexOf('\u017E\u017C\u017A', fromCodePoint(character)) != -1?(normatedWord.impl.append(normatedWord.data, 'z') , normatedWord):(normatedWord.impl.appendNonNull(normatedWord.data, String.fromCharCode(character)) , normatedWord);
  }
  return normatedWord;
}

function NormationLat_0(){
}

defineSeed(109, 99, makeCastMap([Q$Normation]), NormationLat_0);
_.normateWord_0 = function normateWord_9(nonNormatedWord, fromUserInput){
  return $normateWord(nonNormatedWord, fromUserInput);
}
;
function defaultNormation(string, fromUserInput){
  var lowerCaseWord, noPunctuationWord, noSuperflousWhitespaceWord;
  lowerCaseWord = convertToLowerCase(string);
  noPunctuationWord = removePunctuation(lowerCaseWord, fromUserInput);
  noSuperflousWhitespaceWord = filterSuperflousWhitespaces(noPunctuationWord);
  return noSuperflousWhitespaceWord;
}

function NormationNor_0(){
}

defineSeed(111, 99, makeCastMap([Q$Normation]), NormationNor_0);
_.normateWord_0 = function normateWord_10(nonNormatedWord, fromUserInput){
  var charPos, defaultNormatedWord, normatedWord;
  defaultNormatedWord = defaultNormation(nonNormatedWord, fromUserInput);
  normatedWord = new StringBuffer_0;
  for (charPos = 0; charPos < defaultNormatedWord.impl.length_0(defaultNormatedWord.data); ++charPos) {
    $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 230?(normatedWord.impl.append(normatedWord.data, 'ae') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 248?(normatedWord.impl.append(normatedWord.data, 'oe') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 229?(normatedWord.impl.append(normatedWord.data, 'aa') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 244?(normatedWord.impl.append(normatedWord.data, 'o') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 242?(normatedWord.impl.append(normatedWord.data, 'o') , normatedWord):$append(normatedWord, $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos));
  }
  return normatedWord;
}
;
function NormationRus_0(){
}

defineSeed(112, 99, makeCastMap([Q$Normation]), NormationRus_0);
_.normateWord = function normateWord_11(nonNormatedWord){
  var ch, lastChar, normatedWord, normatedWordLength, pos;
  normatedWord = new StringBuffer_0;
  normatedWordLength = 0;
  lastChar = 32;
  for (pos = 0; pos < nonNormatedWord.impl.length_0(nonNormatedWord.data); ++pos) {
    ch = $charAt(nonNormatedWord.impl.toString_0(nonNormatedWord.data), pos);
    if (ch == 769 || ch == 124)
    ;
    else if ($indexOf('!"$\xA7$%&/()=?\xB4`\\{}[]^\xB0+*~#\'-_.:,;<>|@', fromCodePoint(ch)) != -1 || ch == 32) {
      if (normatedWordLength > 0) {
        if (lastChar != 32) {
          lastChar = 32;
          normatedWord.impl.appendNonNull(normatedWord.data, ' ');
          ++normatedWordLength;
        }
      }
    }
     else {
      lastChar = toLowerCase(ch);
      lastChar == 1105 && (lastChar = 1077);
      normatedWord.impl.appendNonNull(normatedWord.data, String.fromCharCode(lastChar));
      ++normatedWordLength;
    }
  }
  lastChar == 32 && normatedWordLength > 0 && $setLength(normatedWord, normatedWordLength - 1);
  return normatedWord;
}
;
function NormationRus2_0(){
}

defineSeed(113, 99, makeCastMap([Q$Normation]), NormationRus2_0);
_.normateWord_0 = function normateWord_12(nonNormatedWord, fromUserInput){
  var charPos, defaultNormatedWord, normatedWord;
  defaultNormatedWord = defaultNormation(nonNormatedWord, fromUserInput);
  normatedWord = new StringBuffer_0;
  for (charPos = 0; charPos < defaultNormatedWord.impl.length_0(defaultNormatedWord.data); ++charPos) {
    $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1072?(normatedWord.impl.append(normatedWord.data, 'a') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1073?(normatedWord.impl.append(normatedWord.data, 'b') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1074?(normatedWord.impl.append(normatedWord.data, 'v') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1075?(normatedWord.impl.append(normatedWord.data, 'g') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1076?(normatedWord.impl.append(normatedWord.data, 'd') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1077?(normatedWord.impl.append(normatedWord.data, 'e') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1105?(normatedWord.impl.append(normatedWord.data, 'yo') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1078?(normatedWord.impl.append(normatedWord.data, 'zh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1079?(normatedWord.impl.append(normatedWord.data, 'z') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1080?(normatedWord.impl.append(normatedWord.data, 'i') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1081?(normatedWord.impl.append(normatedWord.data, 'y') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1082?(normatedWord.impl.append(normatedWord.data, 'k') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1083?(normatedWord.impl.append(normatedWord.data, 'l') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1084?(normatedWord.impl.append(normatedWord.data, 'm') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1085?(normatedWord.impl.append(normatedWord.data, 'n') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1086?(normatedWord.impl.append(normatedWord.data, 'o') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1087?(normatedWord.impl.append(normatedWord.data, 'p') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1088?(normatedWord.impl.append(normatedWord.data, 'r') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1089?(normatedWord.impl.append(normatedWord.data, 's') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1090?(normatedWord.impl.append(normatedWord.data, 't') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1091?(normatedWord.impl.append(normatedWord.data, 'u') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1092?(normatedWord.impl.append(normatedWord.data, 'f') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1093?(normatedWord.impl.append(normatedWord.data, 'kh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1094?(normatedWord.impl.append(normatedWord.data, 'c') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1095?(normatedWord.impl.append(normatedWord.data, 'ch') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1096?(normatedWord.impl.append(normatedWord.data, 'sh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1097?(normatedWord.impl.append(normatedWord.data, 'shh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1098?(normatedWord.impl.append(normatedWord.data, 'x') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1099?(normatedWord.impl.append(normatedWord.data, 'y') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1100?(normatedWord.impl.append(normatedWord.data, 'x') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1101?(normatedWord.impl.append(normatedWord.data, 'eh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1102?(normatedWord.impl.append(normatedWord.data, 'yu') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1103?(normatedWord.impl.append(normatedWord.data, 'ya') , normatedWord):$append(normatedWord, $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos));
  }
  return normatedWord;
}
;
function NormationRusC_0(){
}

defineSeed(114, 99, makeCastMap([Q$Normation]), NormationRusC_0);
_.normateWord_0 = function normateWord_13(nonNormatedWord, fromUserInput){
  var charPos, defaultNormatedWord, normatedWord;
  defaultNormatedWord = defaultNormation(nonNormatedWord, fromUserInput);
  normatedWord = new StringBuffer_0;
  for (charPos = 0; charPos < defaultNormatedWord.impl.length_0(defaultNormatedWord.data); ++charPos) {
    $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1072?(normatedWord.impl.append(normatedWord.data, 'a') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1073?(normatedWord.impl.append(normatedWord.data, 'b') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1074?(normatedWord.impl.append(normatedWord.data, 'v') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1075?(normatedWord.impl.append(normatedWord.data, 'g') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1076?(normatedWord.impl.append(normatedWord.data, 'd') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1077?(normatedWord.impl.append(normatedWord.data, 'e') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1105?(normatedWord.impl.append(normatedWord.data, 'jo') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1078?(normatedWord.impl.append(normatedWord.data, 'z') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1079?(normatedWord.impl.append(normatedWord.data, 'z') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1080?(normatedWord.impl.append(normatedWord.data, 'i') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1081?(normatedWord.impl.append(normatedWord.data, 'j') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1082?(normatedWord.impl.append(normatedWord.data, 'k') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1083?(normatedWord.impl.append(normatedWord.data, 'l') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1084?(normatedWord.impl.append(normatedWord.data, 'm') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1085?(normatedWord.impl.append(normatedWord.data, 'n') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1086?(normatedWord.impl.append(normatedWord.data, 'o') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1087?(normatedWord.impl.append(normatedWord.data, 'p') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1088?(normatedWord.impl.append(normatedWord.data, 'r') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1089?(normatedWord.impl.append(normatedWord.data, 's') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1090?(normatedWord.impl.append(normatedWord.data, 't') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1091?(normatedWord.impl.append(normatedWord.data, 'u') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1092?(normatedWord.impl.append(normatedWord.data, 'f') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1093?(normatedWord.impl.append(normatedWord.data, 'ch') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1094?(normatedWord.impl.append(normatedWord.data, 'c') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1095?(normatedWord.impl.append(normatedWord.data, 'c') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1096?(normatedWord.impl.append(normatedWord.data, 's') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1097?(normatedWord.impl.append(normatedWord.data, 'sc') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1098?(normatedWord.impl.append(normatedWord.data, 'x') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1099?(normatedWord.impl.append(normatedWord.data, 'y') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1100?(normatedWord.impl.append(normatedWord.data, 'x') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1101?(normatedWord.impl.append(normatedWord.data, 'e') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1102?(normatedWord.impl.append(normatedWord.data, 'ju') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1103?(normatedWord.impl.append(normatedWord.data, 'ja') , normatedWord):$append(normatedWord, $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos));
  }
  return normatedWord;
}
;
function NormationUkr_0(){
}

defineSeed(115, 99, makeCastMap([Q$Normation]), NormationUkr_0);
_.normateWord_0 = function normateWord_14(nonNormatedWord, fromUserInput){
  var charPos, defaultNormatedWord, normatedWord;
  defaultNormatedWord = defaultNormation(nonNormatedWord, fromUserInput);
  normatedWord = new StringBuffer_0;
  for (charPos = 0; charPos < defaultNormatedWord.impl.length_0(defaultNormatedWord.data); ++charPos) {
    $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1072?(normatedWord.impl.append(normatedWord.data, 'a') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1073?(normatedWord.impl.append(normatedWord.data, 'b') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1074?(normatedWord.impl.append(normatedWord.data, 'v') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1075?(normatedWord.impl.append(normatedWord.data, 'h') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1076?(normatedWord.impl.append(normatedWord.data, 'd') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1077?(normatedWord.impl.append(normatedWord.data, 'e') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1105?(normatedWord.impl.append(normatedWord.data, 'yo') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1078?(normatedWord.impl.append(normatedWord.data, 'zh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1079?(normatedWord.impl.append(normatedWord.data, 'z') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1080?(normatedWord.impl.append(normatedWord.data, 'i') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1111?(normatedWord.impl.append(normatedWord.data, 'yi') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1081?(normatedWord.impl.append(normatedWord.data, 'y') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1082?(normatedWord.impl.append(normatedWord.data, 'k') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1083?(normatedWord.impl.append(normatedWord.data, 'l') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1084?(normatedWord.impl.append(normatedWord.data, 'm') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1085?(normatedWord.impl.append(normatedWord.data, 'n') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1086?(normatedWord.impl.append(normatedWord.data, 'o') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1087?(normatedWord.impl.append(normatedWord.data, 'p') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1088?(normatedWord.impl.append(normatedWord.data, 'r') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1089?(normatedWord.impl.append(normatedWord.data, 's') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1090?(normatedWord.impl.append(normatedWord.data, 't') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1091?(normatedWord.impl.append(normatedWord.data, 'u') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1092?(normatedWord.impl.append(normatedWord.data, 'f') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1093?(normatedWord.impl.append(normatedWord.data, 'kh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1094?(normatedWord.impl.append(normatedWord.data, 'c') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1095?(normatedWord.impl.append(normatedWord.data, 'ch') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1096?(normatedWord.impl.append(normatedWord.data, 'sh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1097?(normatedWord.impl.append(normatedWord.data, 'shh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1098?(normatedWord.impl.append(normatedWord.data, 'x') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1099?(normatedWord.impl.append(normatedWord.data, 'y') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1100?(normatedWord.impl.append(normatedWord.data, 'x') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1101?(normatedWord.impl.append(normatedWord.data, 'eh') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1102?(normatedWord.impl.append(normatedWord.data, 'yu') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1103?(normatedWord.impl.append(normatedWord.data, 'ya') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1169?(normatedWord.impl.append(normatedWord.data, 'g') , normatedWord):$append(normatedWord, $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos));
  }
  return normatedWord;
}
;
function NormationUkrC_0(){
}

defineSeed(116, 99, makeCastMap([Q$Normation]), NormationUkrC_0);
_.normateWord_0 = function normateWord_15(nonNormatedWord, fromUserInput){
  var charPos, defaultNormatedWord, normatedWord;
  defaultNormatedWord = defaultNormation(nonNormatedWord, fromUserInput);
  normatedWord = new StringBuffer_0;
  for (charPos = 0; charPos < defaultNormatedWord.impl.length_0(defaultNormatedWord.data); ++charPos) {
    $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1072?(normatedWord.impl.append(normatedWord.data, 'a') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1073?(normatedWord.impl.append(normatedWord.data, 'b') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1074?(normatedWord.impl.append(normatedWord.data, 'v') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1075?(normatedWord.impl.append(normatedWord.data, 'h') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1076?(normatedWord.impl.append(normatedWord.data, 'd') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1077?(normatedWord.impl.append(normatedWord.data, 'e') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1105?(normatedWord.impl.append(normatedWord.data, 'jo') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1078?(normatedWord.impl.append(normatedWord.data, 'z') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1079?(normatedWord.impl.append(normatedWord.data, 'z') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1080?(normatedWord.impl.append(normatedWord.data, 'i') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1111?(normatedWord.impl.append(normatedWord.data, 'ji') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1081?(normatedWord.impl.append(normatedWord.data, 'j') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1082?(normatedWord.impl.append(normatedWord.data, 'k') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1083?(normatedWord.impl.append(normatedWord.data, 'l') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1084?(normatedWord.impl.append(normatedWord.data, 'm') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1085?(normatedWord.impl.append(normatedWord.data, 'n') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1086?(normatedWord.impl.append(normatedWord.data, 'o') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1087?(normatedWord.impl.append(normatedWord.data, 'p') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1088?(normatedWord.impl.append(normatedWord.data, 'r') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1089?(normatedWord.impl.append(normatedWord.data, 's') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1090?(normatedWord.impl.append(normatedWord.data, 't') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1091?(normatedWord.impl.append(normatedWord.data, 'u') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1092?(normatedWord.impl.append(normatedWord.data, 'f') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1093?(normatedWord.impl.append(normatedWord.data, 'ch') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1094?(normatedWord.impl.append(normatedWord.data, 'c') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1095?(normatedWord.impl.append(normatedWord.data, 'c') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1096?(normatedWord.impl.append(normatedWord.data, 's') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1097?(normatedWord.impl.append(normatedWord.data, 'sc') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1098?(normatedWord.impl.append(normatedWord.data, 'x') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1099?(normatedWord.impl.append(normatedWord.data, 'y') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1100?(normatedWord.impl.append(normatedWord.data, 'x') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1101?(normatedWord.impl.append(normatedWord.data, 'e') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1102?(normatedWord.impl.append(normatedWord.data, 'ju') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1103?(normatedWord.impl.append(normatedWord.data, 'ja') , normatedWord):$charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos) == 1169?(normatedWord.impl.append(normatedWord.data, 'g') , normatedWord):$append(normatedWord, $charAt(defaultNormatedWord.impl.toString_0(defaultNormatedWord.data), charPos));
  }
  return normatedWord;
}
;
function $clinit_NormationVie(){
  $clinit_NormationVie = nullMethod;
  nguyenamTelex = initValues(_3Ljava_lang_String_2_classLit, makeCastMap([Q$Serializable]), Q$String, ['a', 'e', 'i', 'u', 'o', 'y', 'aa', 'aw', 'ee', 'oo', 'ow', 'uw']);
}

function NormationVie_0(){
  $clinit_NormationVie();
}

defineSeed(117, 99, makeCastMap([Q$Normation]), NormationVie_0);
_.normateWord = function normateWord_16(nonNormatedWord){
  var ch, dau, dauPos, lastChar, normatedWord, normatedWordLength, pos, singleWordLength;
  normatedWord = new StringBuffer_0;
  normatedWordLength = 0;
  singleWordLength = 0;
  lastChar = 32;
  dau = 1;
  for (pos = 0; pos < nonNormatedWord.impl.length_0(nonNormatedWord.data); ++pos) {
    ch = $charAt(nonNormatedWord.impl.toString_0(nonNormatedWord.data), pos);
    dauPos = $indexOf('aA\xE0\xC0\xE1\xC1\u1EA1\u1EA0\u1EA3\u1EA2\xE3\xC3eE\xE8\xC8\xE9\xC9\u1EB9\u1EB8\u1EBB\u1EBA\u1EBD\u1EBCiI\xEC\xCC\xED\xCD\u1ECB\u1ECA\u1EC9\u1EC8\u0129\u0128uU\xF9\xD9\xFA\xDA\u1EE5\u1EE4\u1EE7\u1EE6\u0169\u0168oO\xF2\xD2\xF3\xD3\u1ECD\u1ECC\u1ECF\u1ECE\xF5\xD5yY\u1EF3\u1EF2\xFD\xDD\u1EF5\u1EF4\u1EF7\u1EF6\u1EF9\u1EF8\xE2\xC2\u1EA7\u1EA6\u1EA5\u1EA4\u1EAD\u1EAC\u1EA9\u1EA8\u1EAB\u1EAA\u0103\u0102\u1EB1\u1EB0\u1EAF\u1EAE\u1EB7\u1EB6\u1EB3\u1EB2\u1EB5\u1EB4\xEA\xCA\u1EC1\u1EC0\u1EBF\u1EBE\u1EC7\u1EC6\u1EC3\u1EC2\u1EC5\u1EC4\xF4\xD4\u1ED3\u1ED0\u1ED1\u1ED0\u1ED9\u1ED8\u1ED5\u1ED4\u1ED7\u1ED6\u01A1\u01A0\u1EDD\u1EDC\u1EDB\u1EDA\u1EE3\u1EE2\u1EDF\u1EDE\u1EE1\u1EE0\u01B0\u01AF\u1EEB\u1EEA\u1EE9\u1EE8\u1EF1\u1EF0\u1EED\u1EEC\u1EEF\u1EEE', fromCodePoint(ch));
    if (dauPos >= 0) {
      $append_0(normatedWord, nguyenamTelex[~~(dauPos / 12)]);
      normatedWordLength += nguyenamTelex[~~(dauPos / 12)].length;
      ++singleWordLength;
      dau == 1 && (dau = '\1\1ffssjjrrxx'.charCodeAt(dauPos % 12));
    }
     else if ($indexOf('\u0111\u0110', fromCodePoint(ch)) >= 0) {
      normatedWord.impl.append(normatedWord.data, 'dd');
      normatedWordLength += 2;
      ++singleWordLength;
    }
     else {
      ch = toLowerCase(ch);
      dauPos = $indexOf('\1\1ffssjjrrxx', fromCodePoint(ch));
      if (dauPos >= 0) {
        if (singleWordLength == 0 || singleWordLength == 1 && ch == 114) {
          normatedWord.impl.append(normatedWord.data, 'r');
          ++singleWordLength;
        }
         else 
          dau = ch;
      }
       else if (ch == 119) {
        if (singleWordLength > 0) {
          if (lastChar != 97 && lastChar != 117 && lastChar != 111) {
            normatedWord.impl.append(normatedWord.data, 'uw');
            normatedWordLength += 2;
          }
           else {
            normatedWord.impl.append(normatedWord.data, 'w');
            ++normatedWordLength;
          }
        }
         else {
          normatedWord.impl.append(normatedWord.data, 'uw');
          normatedWordLength += 2;
        }
        ++singleWordLength;
      }
       else if ($indexOf('!"$\xA7$%&/()=?\xB4`\\{}[]^\xB0+*~#\'-_.:,;<>|@', fromCodePoint(ch)) != -1 || ch == 32) {
        if (normatedWordLength > 0 && lastChar != 32) {
          if (dau != 1) {
            normatedWord.impl.appendNonNull(normatedWord.data, String.fromCharCode(dau));
            ++normatedWordLength;
          }
          normatedWord.impl.appendNonNull(normatedWord.data, ' ');
          ++normatedWordLength;
          dau = 1;
          singleWordLength = 0;
        }
        ch = 32;
      }
       else {
        normatedWord.impl.appendNonNull(normatedWord.data, String.fromCharCode(ch));
        ++normatedWordLength;
        ++singleWordLength;
      }
    }
    lastChar = ch;
  }
  dau != 1?(normatedWord.impl.appendNonNull(normatedWord.data, String.fromCharCode(dau)) , normatedWord):lastChar == 32 && normatedWordLength > 0 && $setLength(normatedWord, normatedWordLength - 1);
  return normatedWord;
}
;
var nguyenamTelex;
function $getHiraganaToRomajiTable(this$static){
  var e;
  if (!this$static.hiraganaToRomajiTable) {
    try {
      this$static.hiraganaToRomajiTable = $initialiseTable(this$static, '/char_lists/romaji_hiragana_utf8.txt', true);
    }
     catch ($e0) {
      $e0 = caught($e0);
      if (instanceOf($e0, Q$Exception)) {
        e = $e0;
        $printStackTrace(e);
      }
       else 
        throw $e0;
    }
  }
  return this$static.hiraganaToRomajiTable;
}

function $getKatakanaToRomajiTable(this$static){
  var e;
  if (!this$static.katakanaToRomajiTable) {
    try {
      this$static.katakanaToRomajiTable = $initialiseTable(this$static, '/char_lists/romaji_katakana_utf8.txt', true);
    }
     catch ($e0) {
      $e0 = caught($e0);
      if (instanceOf($e0, Q$Exception)) {
        e = $e0;
        $printStackTrace(e);
      }
       else 
        throw $e0;
    }
  }
  return this$static.katakanaToRomajiTable;
}

function $getRomajiToHiraganaTable(this$static){
  var e;
  if (!this$static.romajiToHiraganaTable) {
    try {
      this$static.romajiToHiraganaTable = $initialiseTable(this$static, '/char_lists/romaji_hiragana_utf8.txt', false);
    }
     catch ($e0) {
      $e0 = caught($e0);
      if (instanceOf($e0, Q$Exception)) {
        e = $e0;
        $printStackTrace(e);
      }
       else 
        throw $e0;
    }
  }
  return this$static.romajiToHiraganaTable;
}

function $getRomajiToKatakanaTable(this$static){
  var e;
  if (!this$static.romajiToKatakanaTable) {
    try {
      this$static.romajiToKatakanaTable = $initialiseTable(this$static, '/char_lists/romaji_katakana_utf8.txt', false);
    }
     catch ($e0) {
      $e0 = caught($e0);
      if (instanceOf($e0, Q$Exception)) {
        e = $e0;
        $printStackTrace(e);
      }
       else 
        throw $e0;
    }
  }
  return this$static.romajiToKatakanaTable;
}

function $initialiseTable(this$static, filename, reverse){
  var file, left, map, right;
  map = new Hashtable_0;
  try {
    file = new CsvFile_0(this$static.dictionaryDataFileISAccess, filename, 61, 'UTF-8', 20480);
    file.fileStorageObj = ($clinit_CsvFile() , fileStorageReader).readFileToFileStorage(file.dictionaryDataFileISAccess, file.fileName, file.charEncoding, file.maxSizeOfFileData);
    while (!$equals(left = $toString_1($getWord(file)), '')) {
      right = $toString_1($getWord(file));
      reverse?right == null?$putNullSlot(map, left):right != null?$putStringValue(map, right, left):$putHashValue(map, null, left, ~~getHashCode_1(null)):left == null?$putNullSlot(map, right):left != null?$putStringValue(map, left, right):$putHashValue(map, null, right, ~~getHashCode_1(null));
    }
  }
   catch ($e0) {
    $e0 = caught($e0);
    if (!instanceOf($e0, Q$DictionaryException))
      throw $e0;
  }
  return map;
}

function $setDictionaryDataFileISAccess(this$static, dictionaryDataFileISAccessParam){
  this$static.dictionaryDataFileISAccess = dictionaryDataFileISAccessParam;
}

function LookupTable_0(){
}

defineSeed(118, 1, {}, LookupTable_0);
_.dictionaryDataFileISAccess = null;
_.hiraganaToRomajiTable = null;
_.katakanaToRomajiTable = null;
_.romajiToHiraganaTable = null;
_.romajiToKatakanaTable = null;
function $convert(this$static, input, charSet, normalMode){
  var converted, i, j, result, searchString, maybeJsoInvocation;
  converted = '';
  for (i = 0; i < input.length;) {
    for (j = 5; j > 0; --j) {
      searchString = input.substr(i, i + j - i);
      normalMode?charSet == 1?(result = $get($getRomajiToKatakanaTable(this$static.lookuptable), searchString)):(result = $get($getRomajiToHiraganaTable(this$static.lookuptable), searchString)):charSet == 1?(result = $get($getKatakanaToRomajiTable(this$static.lookuptable), searchString)):(result = $get($getHiraganaToRomajiTable(this$static.lookuptable), searchString));
      if (result != null) {
        converted += (maybeJsoInvocation = result , isJavaObject(maybeJsoInvocation)?maybeJsoInvocation.toString$():maybeJsoInvocation.toString?maybeJsoInvocation.toString():'[JavaScriptObject]');
        i += j;
        j = 1;
      }
       else {
        if (j == 1) {
          ++i;
          converted += searchString;
        }
      }
    }
  }
  return converted;
}

function $setDictionaryDataFileISAccess_0(this$static, dictionaryDataFileISAccess){
  $setDictionaryDataFileISAccess(this$static.lookuptable, dictionaryDataFileISAccess);
}

function Parser_0(){
  this.lookuptable = new LookupTable_0;
}

defineSeed(119, 1, {}, Parser_0);
_.lookuptable = null;
defineSeed(120, 7, makeCastMap([Q$IOException, Q$Serializable, Q$Exception, Q$Throwable]));
function $read_1(this$static){
  var character;
  if (this$static.pos < this$static.inputStreamContentAsString.length) {
    character = $charAt(this$static.inputStreamContentAsString, this$static.pos);
    ++this$static.pos;
  }
   else {
    character = -1;
  }
  return character;
}

function InputStreamReader_0(in_$, encoding_name){
  var character, fileData, sizeOfFile;
  fileData = initDim(_3B_classLit, makeCastMap([Q$Serializable]), -1, 1000000, 1);
  sizeOfFile = 0;
  do {
    character = $read_0(in_$);
    if (character != -1) {
      fileData[sizeOfFile] = ~~(character << 24) >> 24;
      ++sizeOfFile;
    }
  }
   while (character != -1);
  this.inputStreamContentAsString = _String(fileData, sizeOfFile, encoding_name);
}

defineSeed(121, 1, {}, InputStreamReader_0);
_.inputStreamContentAsString = null;
_.pos = 0;
function UnsupportedEncodingException_0(msg){
  Exception_0.call(this, msg);
}

defineSeed(122, 120, makeCastMap([Q$IOException, Q$Serializable, Q$Exception, Q$Throwable]), UnsupportedEncodingException_0);
function ArithmeticException_0(){
  RuntimeException_1.call(this, 'divide by zero');
}

defineSeed(123, 6, makeCastMap([Q$Serializable, Q$Exception, Q$Throwable]), ArithmeticException_0);
function ArrayStoreException_0(){
  RuntimeException_0.call(this);
}

defineSeed(124, 6, makeCastMap([Q$Serializable, Q$Exception, Q$Throwable]), ArrayStoreException_0);
function digit(c, radix){
  if (radix < 2 || radix > 36) {
    return -1;
  }
  if (c >= 48 && c < 48 + (radix < 10?radix:10)) {
    return c - 48;
  }
  if (c >= 97 && c < radix + 97 - 10) {
    return c - 97 + 10;
  }
  if (c >= 65 && c < radix + 65 - 10) {
    return c - 65 + 10;
  }
  return -1;
}

function toChars(codePoint, dst, dstIndex){
  if (codePoint < 0 || codePoint > 1114111) {
    throw new IllegalArgumentException_0;
  }
  if (codePoint >= 65536) {
    dst[dstIndex++] = 55296 + (~~(codePoint - 65536) >> 10 & 1023) & 65535;
    dst[dstIndex] = 56320 + (codePoint - 65536 & 1023) & 65535;
    return 2;
  }
   else {
    dst[dstIndex] = codePoint & 65535;
    return 1;
  }
}

function toLowerCase(c){
  return String.fromCharCode(c).toLowerCase().charCodeAt(0);
}

function toUpperCase(c){
  return String.fromCharCode(c).toUpperCase().charCodeAt(0);
}

function Class_0(){
}

function createForArray(packageName, className, seedId){
  var clazz;
  clazz = new Class_0;
  clazz.typeName = packageName + className;
  isInstantiable(seedId != 0?-seedId:0) && setClassLiteral(seedId != 0?-seedId:0, clazz);
  clazz.modifiers = 4;
  return clazz;
}

function createForClass(packageName, className, seedId){
  var clazz;
  clazz = new Class_0;
  clazz.typeName = packageName + className;
  isInstantiable(seedId) && setClassLiteral(seedId, clazz);
  return clazz;
}

function getSeedFunction(clazz){
  var func = seedTable[clazz.seedId];
  clazz = null;
  return func;
}

function isInstantiable(seedId){
  return typeof seedId == 'number' && seedId > 0;
}

function setClassLiteral(seedId, clazz){
  var proto;
  clazz.seedId = seedId;
  if (seedId == 2) {
    proto = String.prototype;
  }
   else {
    if (seedId > 0) {
      var seed = getSeedFunction(clazz);
      if (seed) {
        proto = seed.prototype;
      }
       else {
        seed = seedTable[seedId] = function(){
        }
        ;
        seed.___clazz$ = clazz;
        return;
      }
    }
     else {
      return;
    }
  }
  proto.___clazz$ = clazz;
}

defineSeed(126, 1, {}, Class_0);
_.toString$ = function toString_6(){
  return ((this.modifiers & 2) != 0?'interface ':(this.modifiers & 1) != 0?'':'class ') + this.typeName;
}
;
_.modifiers = 0;
_.seedId = 0;
_.typeName = null;
function ClassCastException_0(){
  RuntimeException_0.call(this);
}

defineSeed(127, 6, makeCastMap([Q$Serializable, Q$Exception, Q$Throwable]), ClassCastException_0);
function __parseAndValidateInt(s, radix){
  var i, length_0, startIndex, toReturn;
  if (s == null) {
    throw new NumberFormatException_0('null');
  }
  if (radix < 2 || radix > 36) {
    throw new NumberFormatException_0('radix ' + radix + ' out of range');
  }
  length_0 = s.length;
  startIndex = length_0 > 0 && s.charCodeAt(0) == 45?1:0;
  for (i = startIndex; i < length_0; ++i) {
    if (digit(s.charCodeAt(i), radix) == -1) {
      throw new NumberFormatException_0('For input string: "' + s + '"');
    }
  }
  toReturn = parseInt(s, radix);
  if (isNaN(toReturn)) {
    throw new NumberFormatException_0('For input string: "' + s + '"');
  }
   else if (toReturn < -2147483648 || toReturn > 2147483647) {
    throw new NumberFormatException_0('For input string: "' + s + '"');
  }
  return toReturn;
}

defineSeed(129, 1, makeCastMap([Q$Serializable, Q$Number]));
function IllegalArgumentException_0(){
  RuntimeException_0.call(this);
}

function IllegalArgumentException_1(message){
  RuntimeException_1.call(this, message);
}

defineSeed(130, 6, makeCastMap([Q$Serializable, Q$Exception, Q$Throwable]), IllegalArgumentException_0, IllegalArgumentException_1);
function IndexOutOfBoundsException_0(){
  RuntimeException_0.call(this);
}

function IndexOutOfBoundsException_1(message){
  RuntimeException_1.call(this, message);
}

defineSeed(131, 6, makeCastMap([Q$Serializable, Q$Exception, Q$Throwable]), IndexOutOfBoundsException_0, IndexOutOfBoundsException_1);
function Integer_0(value){
  this.value = value;
}

function numberOfLeadingZeros_0(i){
  var m_0, n, y;
  if (i < 0) {
    return 0;
  }
   else if (i == 0) {
    return 32;
  }
   else {
    y = -(~~i >> 16);
    m_0 = ~~y >> 16 & 16;
    n = 16 - m_0;
    i = ~~i >> m_0;
    y = i - 256;
    m_0 = ~~y >> 16 & 8;
    n += m_0;
    i <<= m_0;
    y = i - 4096;
    m_0 = ~~y >> 16 & 4;
    n += m_0;
    i <<= m_0;
    y = i - 16384;
    m_0 = ~~y >> 16 & 2;
    n += m_0;
    i <<= m_0;
    y = ~~i >> 14;
    m_0 = y & ~(~~y >> 1);
    return n + 2 - m_0;
  }
}

function numberOfTrailingZeros(i){
  var r, rtn;
  if (i == 0) {
    return 32;
  }
   else {
    rtn = 0;
    for (r = 1; (r & i) == 0; r <<= 1) {
      ++rtn;
    }
    return rtn;
  }
}

function toPowerOfTwoString(value){
  var buf, digits, pos;
  buf = initDim(_3C_classLit, makeCastMap([Q$Serializable]), -1, 8, 1);
  digits = ($clinit_Number$__Digits() , digits_0);
  pos = 7;
  if (value >= 0) {
    while (value > 15) {
      buf[pos--] = digits[value & 15];
      value >>= 4;
    }
  }
   else {
    while (pos > 0) {
      buf[pos--] = digits[value & 15];
      value >>= 4;
    }
  }
  buf[pos] = digits[value & 15];
  return __valueOf(buf, pos, 8);
}

function toString_8(value, radix){
  var buf, digits, pos;
  if (radix == 10 || radix < 2 || radix > 36) {
    return '' + value;
  }
  buf = initDim(_3C_classLit, makeCastMap([Q$Serializable]), -1, 33, 1);
  digits = ($clinit_Number$__Digits() , digits_0);
  pos = 32;
  if (value >= 0) {
    while (value >= radix) {
      buf[pos--] = digits[value % radix];
      value = ~~(value / radix);
    }
    buf[pos] = digits[value];
  }
   else {
    while (value <= -radix) {
      buf[pos--] = digits[-(value % radix)];
      value = ~~(value / radix);
    }
    buf[pos--] = digits[-value];
    buf[pos] = 45;
  }
  return __valueOf(buf, pos, 33);
}

function valueOf(i){
  var rebase, result;
  if (i > -129 && i < 128) {
    rebase = i + 128;
    result = ($clinit_Integer$BoxedValues() , boxedValues_0)[rebase];
    !result && (result = boxedValues_0[rebase] = new Integer_0(i));
    return result;
  }
  return new Integer_0(i);
}

defineSeed(132, 129, makeCastMap([Q$Serializable, Q$Comparable, Q$Integer, Q$Number]), Integer_0);
_.equals$ = function equals_2(o){
  return instanceOf(o, Q$Integer) && dynamicCast(o, Q$Integer).value == this.value;
}
;
_.hashCode$ = function hashCode_2(){
  return this.value;
}
;
_.toString$ = function toString_7(){
  return '' + this.value;
}
;
_.value = 0;
function $clinit_Integer$BoxedValues(){
  $clinit_Integer$BoxedValues = nullMethod;
  boxedValues_0 = initDim(_3Ljava_lang_Integer_2_classLit, makeCastMap([Q$Serializable]), Q$Integer, 256, 0);
}

var boxedValues_0;
function NullPointerException_0(){
  RuntimeException_0.call(this);
}

defineSeed(134, 6, makeCastMap([Q$Serializable, Q$Exception, Q$Throwable]), NullPointerException_0);
function $clinit_Number$__Digits(){
  $clinit_Number$__Digits = nullMethod;
  digits_0 = initValues(_3C_classLit, makeCastMap([Q$Serializable]), -1, [48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122]);
}

var digits_0;
function NumberFormatException_0(message){
  IllegalArgumentException_1.call(this, message);
}

defineSeed(136, 130, makeCastMap([Q$Serializable, Q$Exception, Q$NumberFormatException, Q$Throwable]), NumberFormatException_0);
function StackTraceElement_0(methodName, fileName, lineNumber){
  this.className = 'Unknown';
  this.methodName = methodName;
  this.fileName = fileName;
  this.lineNumber = lineNumber;
}

defineSeed(137, 1, makeCastMap([Q$Serializable, Q$StackTraceElement]), StackTraceElement_0);
_.toString$ = function toString_9(){
  return this.className + '.' + this.methodName + '(' + (this.fileName != null?this.fileName:'Unknown Source') + (this.lineNumber >= 0?':' + this.lineNumber:'') + ')';
}
;
_.className = null;
_.fileName = null;
_.lineNumber = 0;
_.methodName = null;
function $charAt(this$static, index){
  return this$static.charCodeAt(index);
}

function $endsWith(this$static, suffix){
  return this$static.lastIndexOf(suffix) != -1 && this$static.lastIndexOf(suffix) == this$static.length - suffix.length;
}

function $equals(this$static, other){
  if (!instanceOf(other, Q$String)) {
    return false;
  }
  return String(this$static) == other;
}

function $equalsIgnoreCase(this$static, other){
  if (other == null)
    return false;
  return this$static == other || this$static.toLowerCase() == other.toLowerCase();
}

function $getChars(dst, dstBegin){
  var srcIdx;
  for (srcIdx = 0; srcIdx < 18; ++srcIdx) {
    dst[dstBegin++] = 'EspernatoEstasBona'.charCodeAt(srcIdx);
  }
}

function $indexOf(this$static, str){
  return this$static.indexOf(str);
}

function $indexOf_0(this$static, str, startIndex){
  return this$static.indexOf(str, startIndex);
}

function $lastIndexOf(this$static, str){
  return this$static.lastIndexOf(str);
}

function $lastIndexOf_0(this$static, str, start){
  return this$static.lastIndexOf(str, start);
}

function $split(this$static, regex, maxMatch){
  var compiled = new RegExp(regex, 'g');
  var out = [];
  var count = 0;
  var trail = this$static;
  var lastTrail = null;
  while (true) {
    var matchObj = compiled.exec(trail);
    if (matchObj == null || trail == '' || count == maxMatch - 1 && maxMatch > 0) {
      out[count] = trail;
      break;
    }
     else {
      out[count] = trail.substring(0, matchObj.index);
      trail = trail.substring(matchObj.index + matchObj[0].length, trail.length);
      compiled.lastIndex = 0;
      if (lastTrail == trail) {
        out[count] = trail.substring(0, 1);
        trail = trail.substring(1);
      }
      lastTrail = trail;
      count++;
    }
  }
  if (maxMatch == 0 && this$static.length > 0) {
    var lastNonEmpty = out.length;
    while (lastNonEmpty > 0 && out[lastNonEmpty - 1] == '') {
      --lastNonEmpty;
    }
    lastNonEmpty < out.length && out.splice(lastNonEmpty, out.length - lastNonEmpty);
  }
  var jr = __createArray(out.length);
  for (var i = 0; i < out.length; ++i) {
    jr[i] = out[i];
  }
  return jr;
}

function $substring(this$static, beginIndex){
  return this$static.substr(beginIndex, this$static.length - beginIndex);
}

function $substring_0(this$static, beginIndex, endIndex){
  return this$static.substr(beginIndex, endIndex - beginIndex);
}

function $trim(this$static){
  if (this$static.length == 0 || this$static[0] > ' ' && this$static[this$static.length - 1] > ' ') {
    return this$static;
  }
  var r1 = this$static.replace(/^(\s*)/, '');
  var r2 = r1.replace(/\s*$/, '');
  return r2;
}

function _String(bytes, len, charset){
  if ($equals('UTF-8', charset)) {
    return utf8ToString(bytes, len);
  }
   else if ($equals('ISO-8859-1', charset) || $equals('ISO-LATIN-1', charset)) {
    return latin1ToString(bytes, len);
  }
   else {
    throw new UnsupportedEncodingException_0('Charset ' + charset + ' not supported');
  }
}

function __createArray(numElements){
  return initDim(_3Ljava_lang_String_2_classLit, makeCastMap([Q$Serializable]), Q$String, numElements, 0);
}

function __valueOf(x, start, end){
  x = x.slice(start, end);
  return String.fromCharCode.apply(null, x);
}

function compareTo(thisStr, otherStr){
  thisStr = String(thisStr);
  if (thisStr == otherStr) {
    return 0;
  }
  return thisStr < otherStr?-1:1;
}

function fromCodePoint(codePoint){
  var hiSurrogate, loSurrogate;
  if (codePoint >= 65536) {
    hiSurrogate = 55296 + (~~(codePoint - 65536) >> 10 & 1023) & 65535;
    loSurrogate = 56320 + (codePoint - 65536 & 1023) & 65535;
    return String.fromCharCode(hiSurrogate) + String.fromCharCode(loSurrogate);
  }
   else {
    return String.fromCharCode(codePoint & 65535);
  }
}

function latin1ToString(bytes, len){
  var chars, i;
  chars = initDim(_3C_classLit, makeCastMap([Q$Serializable]), -1, len, 1);
  for (i = 0; i < len; ++i) {
    chars[i] = bytes[i] & 255 & 65535;
  }
  return valueOf_0(chars);
}

function utf8ToString(bytes, len){
  var b, ch, charCount, chars, count, i, outIdx;
  charCount = 0;
  for (i = 0; i < len;) {
    ++charCount;
    ch = bytes[i];
    if ((ch & 192) == 128) {
      throw new IllegalArgumentException_1('Invalid UTF8 sequence');
    }
     else if ((ch & 128) == 0) {
      ++i;
    }
     else if ((ch & 224) == 192) {
      i += 2;
    }
     else if ((ch & 240) == 224) {
      i += 3;
    }
     else if ((ch & 248) == 240) {
      i += 4;
    }
     else {
      throw new IllegalArgumentException_1('Invalid UTF8 sequence');
    }
    if (i > len) {
      throw new IndexOutOfBoundsException_1('Invalid UTF8 sequence');
    }
  }
  chars = initDim(_3C_classLit, makeCastMap([Q$Serializable]), -1, charCount, 1);
  outIdx = 0;
  count = 0;
  for (i = 0; i < len;) {
    ch = bytes[i++];
    if ((ch & 128) == 0) {
      count = 1;
      ch &= 127;
    }
     else if ((ch & 224) == 192) {
      count = 2;
      ch &= 31;
    }
     else if ((ch & 240) == 224) {
      count = 3;
      ch &= 15;
    }
     else if ((ch & 248) == 240) {
      count = 4;
      ch &= 7;
    }
     else if ((ch & 252) == 248) {
      count = 5;
      ch &= 3;
    }
    while (--count > 0) {
      b = bytes[i++];
      if ((b & 192) != 128) {
        throw new IllegalArgumentException_1('Invalid UTF8 sequence at ' + (i - 1) + ', byte=' + toPowerOfTwoString(b));
      }
      ch = ch << 6 | b & 63;
    }
    outIdx += toChars(ch, chars, outIdx);
  }
  return valueOf_0(chars);
}

function valueOf_0(x){
  return String.fromCharCode.apply(null, x);
}

_ = String.prototype;
_.castableTypeMap$ = makeCastMap([Q$String, Q$Serializable, Q$CharSequence, Q$Comparable]);
_.equals$ = function equals_3(other){
  return $equals(this, other);
}
;
_.hashCode$ = function hashCode_3(){
  return getHashCode_1(this);
}
;
_.toString$ = _.toString;
function $clinit_String$HashCache(){
  $clinit_String$HashCache = nullMethod;
  back_0 = {};
  front = {};
}

function compute(str){
  var hashCode, i, n, nBatch;
  hashCode = 0;
  n = str.length;
  nBatch = n - 4;
  i = 0;
  while (i < nBatch) {
    hashCode = str.charCodeAt(i + 3) + 31 * (str.charCodeAt(i + 2) + 31 * (str.charCodeAt(i + 1) + 31 * (str.charCodeAt(i) + 31 * hashCode))) | 0;
    i += 4;
  }
  while (i < n) {
    hashCode = hashCode * 31 + $charAt(str, i++);
  }
  return hashCode | 0;
}

function getHashCode_1(str){
  $clinit_String$HashCache();
  var key = ':' + str;
  var result = front[key];
  if (result != null) {
    return result;
  }
  result = back_0[key];
  result == null && (result = compute(str));
  increment();
  return front[key] = result;
}

function increment(){
  if (count_0 == 256) {
    back_0 = front;
    front = {};
    count_0 = 0;
  }
  ++count_0;
}

var back_0, count_0 = 0, front;
function $$init(this$static){
  this$static.impl = com_google_gwt_core_client_impl_StringBufferImpl();
  this$static.data = this$static.impl.createData();
}

function $append(this$static, x){
  this$static.impl.appendNonNull(this$static.data, String.fromCharCode(x));
  return this$static;
}

function $append_0(this$static, x){
  this$static.impl.append(this$static.data, x);
  return this$static;
}

function $append_1(this$static, x){
  this$static.impl.appendNonNull(this$static.data, valueOf_0(x));
  return this$static;
}

function $charAt_0(this$static, index){
  return $charAt(this$static.impl.toString_0(this$static.data), index);
}

function $deleteCharAt(this$static, start){
  return this$static.impl.replace_0(this$static.data, start, start + 1, '') , this$static;
}

function $length(this$static){
  return this$static.impl.length_0(this$static.data);
}

function $replace(this$static, start, end, toInsert){
  this$static.impl.replace_0(this$static.data, start, end, toInsert);
  return this$static;
}

function $setLength(this$static, newLength){
  var oldLength;
  oldLength = this$static.impl.length_0(this$static.data);
  newLength < oldLength?(this$static.impl.replace_0(this$static.data, newLength, oldLength, '') , this$static):newLength > oldLength && $append_1(this$static, initDim(_3C_classLit, makeCastMap([Q$Serializable]), -1, newLength - oldLength, 1));
}

function $toString_1(this$static){
  return this$static.impl.toString_0(this$static.data);
}

function StringBuffer_0(){
  $$init(this);
}

function StringBuffer_1(){
  $$init(this);
}

function StringBuffer_2(s){
  $$init(this);
  this.impl.append(this.data, s);
}

defineSeed(139, 1, makeCastMap([Q$CharSequence]), StringBuffer_0, StringBuffer_1, StringBuffer_2);
_.toString$ = function toString_10(){
  return $toString_1(this);
}
;
function currentTimeMillis0(){
  return (new Date).getTime();
}

function UnsupportedOperationException_0(message){
  RuntimeException_1.call(this, message);
}

defineSeed(141, 6, makeCastMap([Q$Serializable, Q$Exception, Q$Throwable]), UnsupportedOperationException_0);
function $advanceToFind(iter, o){
  var t;
  while (iter.hasNext()) {
    t = iter.next();
    if (o == null?t == null:equals__devirtual$(o, t)) {
      return iter;
    }
  }
  return null;
}

function $toString_2(this$static){
  var comma, iter, sb, value;
  sb = new StringBuffer_0;
  comma = null;
  sb.impl.append(sb.data, '[');
  iter = this$static.iterator();
  while (iter.hasNext()) {
    comma != null?(sb.impl.append(sb.data, comma) , sb):(comma = ', ');
    value = iter.next();
    sb.impl.append(sb.data, value === this$static?'(this Collection)':'' + value);
  }
  sb.impl.append(sb.data, ']');
  return sb.impl.toString_0(sb.data);
}

defineSeed(142, 1, {});
_.add = function add_0(o){
  throw new UnsupportedOperationException_0('Add not supported on this collection');
}
;
_.contains = function contains(o){
  var iter;
  iter = $advanceToFind(this.iterator(), o);
  return !!iter;
}
;
_.toString$ = function toString_11(){
  return $toString_2(this);
}
;
defineSeed(144, 142, makeCastMap([Q$Set]));
_.equals$ = function equals_4(o){
  var iter, other, otherItem;
  if (o === this) {
    return true;
  }
  if (!instanceOf(o, Q$Set)) {
    return false;
  }
  other = dynamicCast(o, Q$Set);
  if (other.this$0.size_0 != this.size_1()) {
    return false;
  }
  for (iter = new AbstractHashMap$EntrySetIterator_0(other.this$0); $hasNext(iter.iter);) {
    otherItem = dynamicCast($next(iter.iter), Q$Map$Entry);
    if (!this.contains(otherItem)) {
      return false;
    }
  }
  return true;
}
;
_.hashCode$ = function hashCode_4(){
  var hashCode, iter, next;
  hashCode = 0;
  for (iter = this.iterator(); iter.hasNext();) {
    next = iter.next();
    if (next != null) {
      hashCode += hashCode__devirtual$(next);
      hashCode = ~~hashCode;
    }
  }
  return hashCode;
}
;
function AbstractHashMap$EntrySet_0(this$0){
  this.this$0 = this$0;
}

defineSeed(143, 144, makeCastMap([Q$Set]), AbstractHashMap$EntrySet_0);
_.contains = function contains_0(o){
  var entry, key, value;
  if (instanceOf(o, Q$Map$Entry)) {
    entry = dynamicCast(o, Q$Map$Entry);
    key = entry.getKey();
    if ($containsKey(this.this$0, key)) {
      value = $get(this.this$0, key);
      return this.this$0.equals(entry.getValue(), value);
    }
  }
  return false;
}
;
_.iterator = function iterator(){
  return new AbstractHashMap$EntrySetIterator_0(this.this$0);
}
;
_.size_1 = function size_3(){
  return this.this$0.size_0;
}
;
_.this$0 = null;
function AbstractHashMap$EntrySetIterator_0(this$0){
  var list;
  list = new ArrayList_0;
  this$0.nullSlotLive && $add_0(list, new AbstractHashMap$MapEntryNull_0(this$0));
  $addAllStringEntries(this$0, list);
  $addAllHashEntries(this$0, list);
  this.iter = new AbstractList$IteratorImpl_0(list);
}

defineSeed(145, 1, {}, AbstractHashMap$EntrySetIterator_0);
_.hasNext = function hasNext(){
  return $hasNext(this.iter);
}
;
_.next = function next_0(){
  return dynamicCast($next(this.iter), Q$Map$Entry);
}
;
_.iter = null;
defineSeed(147, 1, makeCastMap([Q$Map$Entry]));
_.equals$ = function equals_5(other){
  var entry;
  if (instanceOf(other, Q$Map$Entry)) {
    entry = dynamicCast(other, Q$Map$Entry);
    if (equalsWithNullCheck(this.getKey(), entry.getKey()) && equalsWithNullCheck(this.getValue(), entry.getValue())) {
      return true;
    }
  }
  return false;
}
;
_.hashCode$ = function hashCode_5(){
  var keyHash, valueHash;
  keyHash = 0;
  valueHash = 0;
  this.getKey() != null && (keyHash = hashCode__devirtual$(this.getKey()));
  this.getValue() != null && (valueHash = hashCode__devirtual$(this.getValue()));
  return keyHash ^ valueHash;
}
;
_.toString$ = function toString_12(){
  return this.getKey() + '=' + this.getValue();
}
;
function AbstractHashMap$MapEntryNull_0(this$0){
  this.this$0 = this$0;
}

defineSeed(146, 147, makeCastMap([Q$Map$Entry]), AbstractHashMap$MapEntryNull_0);
_.getKey = function getKey(){
  return null;
}
;
_.getValue = function getValue(){
  return this.this$0.nullSlot;
}
;
_.setValue = function setValue(object){
  return $putNullSlot(this.this$0, object);
}
;
_.this$0 = null;
function AbstractHashMap$MapEntryString_0(this$0, key){
  this.this$0 = this$0;
  this.key = key;
}

defineSeed(148, 147, makeCastMap([Q$Map$Entry]), AbstractHashMap$MapEntryString_0);
_.getKey = function getKey_0(){
  return this.key;
}
;
_.getValue = function getValue_0(){
  return $getStringValue(this.this$0, this.key);
}
;
_.setValue = function setValue_0(object){
  return $putStringValue(this.this$0, this.key, object);
}
;
_.key = null;
_.this$0 = null;
function checkIndex(index, size){
  (index < 0 || index >= size) && indexOutOfBounds(index, size);
}

function indexOutOfBounds(index, size){
  throw new IndexOutOfBoundsException_1('Index: ' + index + ', Size: ' + size);
}

defineSeed(149, 142, makeCastMap([Q$List]));
_.add_0 = function add_1(index, element){
  throw new UnsupportedOperationException_0('Add not supported on this list');
}
;
_.add = function add_2(obj){
  this.add_0(this.size_1(), obj);
  return true;
}
;
_.equals$ = function equals_6(o){
  var elem, elemOther, iter, iterOther, other;
  if (o === this) {
    return true;
  }
  if (!instanceOf(o, Q$List)) {
    return false;
  }
  other = dynamicCast(o, Q$List);
  if (this.size_1() != other.size_1()) {
    return false;
  }
  iter = this.iterator();
  iterOther = other.iterator();
  while (iter.i < iter.this$0.size_1()) {
    elem = $next(iter);
    elemOther = $next(iterOther);
    if (!(elem == null?elemOther == null:equals__devirtual$(elem, elemOther))) {
      return false;
    }
  }
  return true;
}
;
_.hashCode$ = function hashCode_6(){
  var iter, k, obj;
  k = 1;
  iter = this.iterator();
  while (iter.i < iter.this$0.size_1()) {
    obj = $next(iter);
    k = 31 * k + (obj == null?0:hashCode__devirtual$(obj));
    k = ~~k;
  }
  return k;
}
;
_.iterator = function iterator_0(){
  return new AbstractList$IteratorImpl_0(this);
}
;
function $hasNext(this$static){
  return this$static.i < this$static.this$0.size_1();
}

function $next(this$static){
  if (this$static.i >= this$static.this$0.size_1()) {
    throw new NoSuchElementException_0;
  }
  return this$static.this$0.get_0(this$static.i++);
}

function AbstractList$IteratorImpl_0(this$0){
  this.this$0 = this$0;
}

defineSeed(150, 1, {}, AbstractList$IteratorImpl_0);
_.hasNext = function hasNext_0(){
  return $hasNext(this);
}
;
_.next = function next_1(){
  return $next(this);
}
;
_.i = 0;
_.this$0 = null;
function $$init_0(this$static){
  this$static.array = initDim(_3Ljava_lang_Object_2_classLit, makeCastMap([Q$Serializable]), Q$Object, 0, 0);
}

function $add(this$static, index, o){
  (index < 0 || index > this$static.size_0) && indexOutOfBounds(index, this$static.size_0);
  splice_1(this$static.array, index, 0, o);
  ++this$static.size_0;
}

function $add_0(this$static, o){
  setCheck(this$static.array, this$static.size_0++, o);
  return true;
}

function $clear(this$static){
  this$static.array = initDim(_3Ljava_lang_Object_2_classLit, makeCastMap([Q$Serializable]), Q$Object, 0, 0);
  this$static.size_0 = 0;
}

function $get_0(this$static, index){
  checkIndex(index, this$static.size_0);
  return this$static.array[index];
}

function $indexOf_1(this$static, o, index){
  for (; index < this$static.size_0; ++index) {
    if (equalsWithNullCheck(o, this$static.array[index])) {
      return index;
    }
  }
  return -1;
}

function $remove(this$static, index){
  var previous;
  previous = (checkIndex(index, this$static.size_0) , this$static.array[index]);
  splice_0(this$static.array, index, 1);
  --this$static.size_0;
  return previous;
}

function ArrayList_0(){
  $$init_0(this);
}

function ArrayList_1(){
  $$init_0(this);
  this.array.length = 2;
}

function splice_0(array, index, deleteCount){
  array.splice(index, deleteCount);
}

function splice_1(array, index, deleteCount, value){
  array.splice(index, deleteCount, value);
}

defineSeed(151, 149, makeCastMap([Q$Serializable, Q$List]), ArrayList_0, ArrayList_1);
_.add_0 = function add_3(index, o){
  $add(this, index, o);
}
;
_.add = function add_4(o){
  return $add_0(this, o);
}
;
_.contains = function contains_1(o){
  return $indexOf_1(this, o, 0) != -1;
}
;
_.get_0 = function get_1(index){
  return $get_0(this, index);
}
;
_.size_1 = function size_4(){
  return this.size_0;
}
;
_.size_0 = 0;
function enumeration(c){
  var it;
  it = new AbstractList$IteratorImpl_0(c);
  return new Collections$2_0(it);
}

function Collections$2_0(val$it){
  this.val$it = val$it;
}

defineSeed(153, 1, {}, Collections$2_0);
_.val$it = null;
function EmptyStackException_0(){
  RuntimeException_0.call(this);
}

defineSeed(154, 6, makeCastMap([Q$Serializable, Q$Exception, Q$Throwable]), EmptyStackException_0);
function MapEntryImpl_0(key, value){
  this.key = key;
  this.value = value;
}

defineSeed(155, 147, makeCastMap([Q$Map$Entry]), MapEntryImpl_0);
_.getKey = function getKey_1(){
  return this.key;
}
;
_.getValue = function getValue_1(){
  return this.value;
}
;
_.setValue = function setValue_1(value){
  var old;
  old = this.value;
  this.value = value;
  return old;
}
;
_.key = null;
_.value = null;
function NoSuchElementException_0(){
  RuntimeException_0.call(this);
}

defineSeed(156, 6, makeCastMap([Q$Serializable, Q$Exception, Q$Throwable]), NoSuchElementException_0);
function $add_1(this$static, o){
  return $add_0(this$static.arrayList, o);
}

function $addElement(this$static, o){
  $add_0(this$static.arrayList, o);
}

function $elementAt(this$static, index){
  return $get_0(this$static.arrayList, index);
}

function $get_1(this$static, index){
  return $get_0(this$static.arrayList, index);
}

function $insertElementAt(this$static, o, index){
  $add(this$static.arrayList, index, o);
}

function $remove_0(this$static, index){
  return $remove(this$static.arrayList, index);
}

function Vector_0(){
  this.arrayList = new ArrayList_0;
}

function Vector_1(){
  this.arrayList = new ArrayList_1;
}

defineSeed(158, 149, makeCastMap([Q$Serializable, Q$List]), Vector_0, Vector_1);
_.add_0 = function add_5(index, o){
  $add(this.arrayList, index, o);
}
;
_.add = function add_6(o){
  return $add_0(this.arrayList, o);
}
;
_.contains = function contains_2(elem){
  return $indexOf_1(this.arrayList, elem, 0) != -1;
}
;
_.get_0 = function get_2(index){
  return $get_0(this.arrayList, index);
}
;
_.iterator = function iterator_1(){
  return new AbstractList$IteratorImpl_0(this.arrayList);
}
;
_.size_1 = function size_5(){
  return this.arrayList.size_0;
}
;
_.toString$ = function toString_13(){
  return $toString_2(this.arrayList);
}
;
_.arrayList = null;
function $peek(this$static){
  var sz;
  sz = this$static.arrayList.size_0;
  if (sz > 0) {
    return $get_0(this$static.arrayList, sz - 1);
  }
   else {
    throw new EmptyStackException_0;
  }
}

function $pop(this$static){
  var sz;
  sz = this$static.arrayList.size_0;
  if (sz > 0) {
    return $remove(this$static.arrayList, sz - 1);
  }
   else {
    throw new EmptyStackException_0;
  }
}

function Stack_0(){
  Vector_0.call(this);
}

defineSeed(157, 158, makeCastMap([Q$Serializable, Q$List]), Stack_0);
function equalsWithNullCheck(a, b){
  return maskUndefined(a) === maskUndefined(b) || a != null && equals__devirtual$(a, b);
}

var $entry = entry_0;
function gwtOnLoad(errFn, modName, modBase, softPermutationId){
  $moduleName = modName;
  $moduleBase = modBase;
  permutationId = softPermutationId;
  if (errFn)
    try {
      $entry(init)();
    }
     catch (e) {
      errFn(modName);
    }
   else {
    $entry(init)();
  }
}

var Ljava_lang_Object_2_classLit = createForClass('java.lang.', 'Object', 1), Lcom_google_gwt_core_client_JavaScriptObject_2_classLit = createForClass('com.google.gwt.core.client.', 'JavaScriptObject$', 9), _3Ljava_lang_Object_2_classLit = createForArray('[Ljava.lang.', 'Object;', 163), _3Z_classLit = createForArray('', '[Z', 165), Ljava_lang_Throwable_2_classLit = createForClass('java.lang.', 'Throwable', 8), Ljava_lang_Exception_2_classLit = createForClass('java.lang.', 'Exception', 7), Ljava_lang_RuntimeException_2_classLit = createForClass('java.lang.', 'RuntimeException', 6), Ljava_lang_StackTraceElement_2_classLit = createForClass('java.lang.', 'StackTraceElement', 137), _3Ljava_lang_StackTraceElement_2_classLit = createForArray('[Ljava.lang.', 'StackTraceElement;', 166), Lcom_google_gwt_lang_LongLibBase$LongEmul_2_classLit = createForClass('com.google.gwt.lang.', 'LongLibBase$LongEmul', 39), _3Lcom_google_gwt_lang_LongLibBase$LongEmul_2_classLit = createForArray('[Lcom.google.gwt.lang.', 'LongLibBase$LongEmul;', 167), Lcom_google_gwt_lang_SeedUtil_2_classLit = createForClass('com.google.gwt.lang.', 'SeedUtil', 40), Lde_kugihan_dictionaryformids_client_TranslationLayerGWT_2_classLit = createForClass('de.kugihan.dictionaryformids.client.', 'TranslationLayerGWT', 51), Ljava_lang_Number_2_classLit = createForClass('java.lang.', 'Number', 129), _3C_classLit = createForArray('', '[C', 168), Ljava_lang_Class_2_classLit = createForClass('java.lang.', 'Class', 126), Ljava_lang_Integer_2_classLit = createForClass('java.lang.', 'Integer', 132), _3Ljava_lang_Integer_2_classLit = createForArray('[Ljava.lang.', 'Integer;', 169), Ljava_lang_String_2_classLit = createForClass('java.lang.', 'String', 2), _3Ljava_lang_String_2_classLit = createForArray('[Ljava.lang.', 'String;', 164), Ljava_lang_ClassCastException_2_classLit = createForClass('java.lang.', 'ClassCastException', 127), Ljava_lang_ArrayStoreException_2_classLit = createForClass('java.lang.', 'ArrayStoreException', 124), Lcom_google_gwt_core_client_JavaScriptException_2_classLit = createForClass('com.google.gwt.core.client.', 'JavaScriptException', 5), Lcom_google_gwt_useragent_client_UserAgentAsserter_1UserAgentPropertyImplIe6_2_classLit = createForClass('com.google.gwt.useragent.client.', 'UserAgentAsserter_UserAgentPropertyImplIe6', 46), Lcom_google_gwt_useragent_client_UserAgentAsserter_1UserAgentPropertyImplIe8_2_classLit = createForClass('com.google.gwt.useragent.client.', 'UserAgentAsserter_UserAgentPropertyImplIe8', 47), Lcom_google_gwt_useragent_client_UserAgentAsserter_1UserAgentPropertyImplGecko1_18_2_classLit = createForClass('com.google.gwt.useragent.client.', 'UserAgentAsserter_UserAgentPropertyImplGecko1_8', 45), Lcom_google_gwt_useragent_client_UserAgentAsserter_1UserAgentPropertyImplIe9_2_classLit = createForClass('com.google.gwt.useragent.client.', 'UserAgentAsserter_UserAgentPropertyImplIe9', 48), Lcom_google_gwt_useragent_client_UserAgentAsserter_1UserAgentPropertyImplOpera_2_classLit = createForClass('com.google.gwt.useragent.client.', 'UserAgentAsserter_UserAgentPropertyImplOpera', 49), Lcom_google_gwt_useragent_client_UserAgentAsserter_1UserAgentPropertyImplSafari_2_classLit = createForClass('com.google.gwt.useragent.client.', 'UserAgentAsserter_UserAgentPropertyImplSafari', 50), Lde_kugihan_dictionaryformids_general_Util_2_classLit = createForClass('de.kugihan.dictionaryformids.general.', 'Util', 81), Lde_kugihan_dictionaryformids_general_UtilJs_2_classLit = createForClass('de.kugihan.dictionaryformids.general.', 'UtilJs', 82), Lde_kugihan_dictionaryformids_hmi_1common_content_ContentParser_2_classLit = createForClass('de.kugihan.dictionaryformids.hmi_common.content.', 'ContentParser', 83), Ljava_io_InputStream_2_classLit = createForClass('java.io.', 'InputStream', 68), Lde_kugihan_dictionaryformids_dataaccess_fileaccess_HTRInputStream_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.fileaccess.', 'HTRInputStream', 67), _3B_classLit = createForArray('', '[B', 170), Lde_kugihan_dictionaryformids_general_DictionaryException_2_classLit = createForClass('de.kugihan.dictionaryformids.general.', 'DictionaryException', 73), Lde_kugihan_dictionaryformids_dataaccess_CsvFile_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.', 'CsvFile', 52), Lde_kugihan_dictionaryformids_dataaccess_DefaultFileStorageReader_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.', 'DefaultFileStorageReader', 54), Lde_kugihan_dictionaryformids_dataaccess_HTRFileStorageReader_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.', 'HTRFileStorageReader', 58), Lde_kugihan_dictionaryformids_dataaccess_GetISO88591Character_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.', 'GetISO88591Character', 56), Lde_kugihan_dictionaryformids_dataaccess_GetUTF8Character_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.', 'GetUTF8Character', 57), Lde_kugihan_dictionaryformids_dataaccess_DictionaryDataFile_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.', 'DictionaryDataFile', 55), Lde_kugihan_dictionaryformids_dataaccess_LanguageDefinition_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.', 'LanguageDefinition', 59), _3Lde_kugihan_dictionaryformids_dataaccess_LanguageDefinition_2_classLit = createForArray('[Lde.kugihan.dictionaryformids.dataaccess.', 'LanguageDefinition;', 171), Lde_kugihan_dictionaryformids_dataaccess_content_ContentDefinition_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.content.', 'ContentDefinition', 61), _3Lde_kugihan_dictionaryformids_dataaccess_content_ContentDefinition_2_classLit = createForArray('[Lde.kugihan.dictionaryformids.dataaccess.content.', 'ContentDefinition;', 172), Lde_kugihan_dictionaryformids_translation_normation_Normation_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'Normation', 99), Lde_kugihan_dictionaryformids_translation_TranslationThreadCallback_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.', 'TranslationThreadCallback', 96), Lde_kugihan_dictionaryformids_dataaccess_fileaccess_DfMInputStreamAccess_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.fileaccess.', 'DfMInputStreamAccess', 66), Lde_kugihan_dictionaryformids_dataaccess_fileaccess_HTRInputStreamAccess_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.fileaccess.', 'HTRInputStreamAccess', 69), Ljava_lang_ArithmeticException_2_classLit = createForClass('java.lang.', 'ArithmeticException', 123), Lcom_google_gwt_core_client_impl_StringBufferImpl_2_classLit = createForClass('com.google.gwt.core.client.impl.', 'StringBufferImpl', 23), Lde_kugihan_dictionaryformids_dataaccess_content_FontStyle_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.content.', 'FontStyle', 62), Lde_kugihan_dictionaryformids_translation_TranslationParameters_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.', 'TranslationParameters', 93), Lde_kugihan_dictionaryformids_translation_TranslationParametersBatch_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.', 'TranslationParametersBatch', 94), Lde_kugihan_dictionaryformids_hmi_1common_content_StringColourItemText_2_classLit = createForClass('de.kugihan.dictionaryformids.hmi_common.content.', 'StringColourItemText', 84), Lde_kugihan_dictionaryformids_translation_TextOfLanguage_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.', 'TextOfLanguage', 90), Lde_kugihan_dictionaryformids_hmi_1common_content_StringColourItemTextPart_2_classLit = createForClass('de.kugihan.dictionaryformids.hmi_common.content.', 'StringColourItemTextPart', 85), Lde_kugihan_dictionaryformids_dataaccess_content_RGBColour_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.content.', 'RGBColour', 64), Ljava_util_AbstractCollection_2_classLit = createForClass('java.util.', 'AbstractCollection', 142), Ljava_util_AbstractList_2_classLit = createForClass('java.util.', 'AbstractList', 149), Ljava_util_Vector_2_classLit = createForClass('java.util.', 'Vector', 158), Ljava_util_AbstractList$IteratorImpl_2_classLit = createForClass('java.util.', 'AbstractList$IteratorImpl', 150), Lde_kugihan_dictionaryformids_dataaccess_content_SelectionMode_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.content.', 'SelectionMode', 65), Lcom_google_gwt_core_client_impl_StackTraceCreator$Collector_2_classLit = createForClass('com.google.gwt.core.client.impl.', 'StackTraceCreator$Collector', 18), Lcom_google_gwt_core_client_impl_StackTraceCreator$CollectorMoz_2_classLit = createForClass('com.google.gwt.core.client.impl.', 'StackTraceCreator$CollectorMoz', 20), Lcom_google_gwt_core_client_impl_StackTraceCreator$CollectorChrome_2_classLit = createForClass('com.google.gwt.core.client.impl.', 'StackTraceCreator$CollectorChrome', 19), Lcom_google_gwt_core_client_impl_StackTraceCreator$CollectorChromeNoSourceMap_2_classLit = createForClass('com.google.gwt.core.client.impl.', 'StackTraceCreator$CollectorChromeNoSourceMap', 21), Lcom_google_gwt_core_client_impl_StackTraceCreator$CollectorOpera_2_classLit = createForClass('com.google.gwt.core.client.impl.', 'StackTraceCreator$CollectorOpera', 22), Lcom_google_gwt_core_client_impl_StringBufferImplArrayBase_2_classLit = createForClass('com.google.gwt.core.client.impl.', 'StringBufferImplArrayBase', 26), Lcom_google_gwt_core_client_impl_StringBufferImplArray_2_classLit = createForClass('com.google.gwt.core.client.impl.', 'StringBufferImplArray', 25), Lcom_google_gwt_core_client_impl_StringBufferImplAppend_2_classLit = createForClass('com.google.gwt.core.client.impl.', 'StringBufferImplAppend', 24), Lcom_google_gwt_core_client_Scheduler_2_classLit = createForClass('com.google.gwt.core.client.', 'Scheduler', 13), Lcom_google_gwt_core_client_impl_SchedulerImpl_2_classLit = createForClass('com.google.gwt.core.client.impl.', 'SchedulerImpl', 15), Ljava_util_Stack_2_classLit = createForClass('java.util.', 'Stack', 157), Ljava_lang_StringBuffer_2_classLit = createForClass('java.lang.', 'StringBuffer', 139), Ljava_util_AbstractMap_2_classLit = createForClass('java.util.', 'AbstractMap', 80), Ljava_util_AbstractHashMap_2_classLit = createForClass('java.util.', 'AbstractHashMap', 79), Ljava_util_HashMap_2_classLit = createForClass('java.util.', 'HashMap', 78), Ljava_util_Hashtable_2_classLit = createForClass('java.util.', 'Hashtable', 77), Ljava_util_AbstractSet_2_classLit = createForClass('java.util.', 'AbstractSet', 144), Ljava_util_AbstractHashMap$EntrySet_2_classLit = createForClass('java.util.', 'AbstractHashMap$EntrySet', 143), Ljava_util_AbstractHashMap$EntrySetIterator_2_classLit = createForClass('java.util.', 'AbstractHashMap$EntrySetIterator', 145), Ljava_util_AbstractMapEntry_2_classLit = createForClass('java.util.', 'AbstractMapEntry', 147), Ljava_util_AbstractHashMap$MapEntryNull_2_classLit = createForClass('java.util.', 'AbstractHashMap$MapEntryNull', 146), Ljava_util_AbstractHashMap$MapEntryString_2_classLit = createForClass('java.util.', 'AbstractHashMap$MapEntryString', 148), Lde_kugihan_dictionaryformids_dataaccess_CsvFileCache_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.', 'CsvFileCache', 53), Ljava_util_ArrayList_2_classLit = createForClass('java.util.', 'ArrayList', 151), Ljava_lang_IllegalArgumentException_2_classLit = createForClass('java.lang.', 'IllegalArgumentException', 130), Ljava_lang_NumberFormatException_2_classLit = createForClass('java.lang.', 'NumberFormatException', 136), Ljava_lang_NullPointerException_2_classLit = createForClass('java.lang.', 'NullPointerException', 134), Lde_kugihan_dictionaryformids_general_ClassMethodBase_2_classLit = createForClass('de.kugihan.dictionaryformids.general.', 'ClassMethodBase', 70), Lde_kugihan_dictionaryformids_general_ClassMethodImpl_2_classLit = createForClass('de.kugihan.dictionaryformids.general.', 'ClassMethodImpl', 71), Ljava_util_Collections$2_2_classLit = createForClass('java.util.', 'Collections$2', 153), Ljava_io_IOException_2_classLit = createForClass('java.io.', 'IOException', 120), Lde_kugihan_dictionaryformids_general_Properties_2_classLit = createForClass('de.kugihan.dictionaryformids.general.', 'Properties', 76), Lde_kugihan_dictionaryformids_general_CouldNotOpenFileException_2_classLit = createForClass('de.kugihan.dictionaryformids.general.', 'CouldNotOpenFileException', 72), Lde_kugihan_dictionaryformids_general_CouldNotOpenPropertyFileException_2_classLit = createForClass('de.kugihan.dictionaryformids.general.', 'CouldNotOpenPropertyFileException', 74), Lde_kugihan_dictionaryformids_general_DictionaryClassNotLoadedException_2_classLit = createForClass('de.kugihan.dictionaryformids.general.', 'DictionaryClassNotLoadedException', 75), Ljava_lang_UnsupportedOperationException_2_classLit = createForClass('java.lang.', 'UnsupportedOperationException', 141), Ljava_lang_Thread_2_classLit = createForClass('java.lang.', 'Thread', 98), Lde_kugihan_dictionaryformids_translation_TranslationThreadJava_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.', 'TranslationThreadJava', 97), Ljava_io_InputStreamReader_2_classLit = createForClass('java.io.', 'InputStreamReader', 121), Ljava_io_UnsupportedEncodingException_2_classLit = createForClass('java.io.', 'UnsupportedEncodingException', 122), Ljava_util_EmptyStackException_2_classLit = createForClass('java.util.', 'EmptyStackException', 154), Ljava_lang_IndexOutOfBoundsException_2_classLit = createForClass('java.lang.', 'IndexOutOfBoundsException', 131), Lde_kugihan_dictionaryformids_translation_normation_NormationBul_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationBul', 100), Lde_kugihan_dictionaryformids_translation_normation_NormationCyr1_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationCyr1', 101), Lde_kugihan_dictionaryformids_translation_normation_NormationCyr2_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationCyr2', 102), Lde_kugihan_dictionaryformids_translation_normation_NormationEng_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationEng', 103), Lde_kugihan_dictionaryformids_translation_normation_NormationEng2_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationEng2', 104), Lde_kugihan_dictionaryformids_translation_normation_NormationEpo_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationEpo', 105), Lde_kugihan_dictionaryformids_translation_normation_NormationFil_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationFil', 106), Lde_kugihan_dictionaryformids_translation_normation_NormationGer_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationGer', 107), Lde_kugihan_dictionaryformids_translation_normation_NormationJpn_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationJpn', 108), Lde_kugihan_dictionaryformids_translation_normation_NormationLat_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationLat', 109), Lde_kugihan_dictionaryformids_translation_normation_NormationNor_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationNor', 111), Lde_kugihan_dictionaryformids_translation_normation_NormationRus_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationRus', 112), Lde_kugihan_dictionaryformids_translation_normation_NormationRus2_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationRus2', 113), Lde_kugihan_dictionaryformids_translation_normation_NormationRusC_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationRusC', 114), Lde_kugihan_dictionaryformids_translation_normation_NormationUkr_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationUkr', 115), Lde_kugihan_dictionaryformids_translation_normation_NormationUkrC_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationUkrC', 116), Lde_kugihan_dictionaryformids_translation_normation_NormationVie_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.', 'NormationVie', 117), Lde_kugihan_dictionaryformids_translation_Translation_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.', 'Translation', 91), Ljava_util_NoSuchElementException_2_classLit = createForClass('java.util.', 'NoSuchElementException', 156), Ljava_util_MapEntryImpl_2_classLit = createForClass('java.util.', 'MapEntryImpl', 155), Lde_kugihan_dictionaryformids_translation_normation_normationjpn_Parser_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.normationjpn.', 'Parser', 119), Lde_kugihan_dictionaryformids_translation_TranslationResult_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.', 'TranslationResult', 95), Lde_kugihan_dictionaryformids_translation_normation_normationjpn_LookupTable_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.normation.normationjpn.', 'LookupTable', 118), Lde_kugihan_dictionaryformids_translation_SearchedWord_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.', 'SearchedWord', 88), Lde_kugihan_dictionaryformids_translation_SingleTranslation_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.', 'SingleTranslation', 89), Lde_kugihan_dictionaryformids_dataaccess_StringFileStorage_2_classLit = createForClass('de.kugihan.dictionaryformids.dataaccess.', 'StringFileStorage', 60), Lde_kugihan_dictionaryformids_translation_SearchIndicator_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.', 'SearchIndicator', 87), Lde_kugihan_dictionaryformids_translation_DirectoryFileLocation_2_classLit = createForClass('de.kugihan.dictionaryformids.translation.', 'DirectoryFileLocation', 86);
if (DfMTranslationLayer) DfMTranslationLayer.onScriptLoad(gwtOnLoad);})();