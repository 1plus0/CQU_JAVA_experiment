(function(e){function n(n){for(var r,a,u=n[0],i=n[1],l=n[2],f=0,d=[];f<u.length;f++)a=u[f],Object.prototype.hasOwnProperty.call(o,a)&&o[a]&&d.push(o[a][0]),o[a]=0;for(r in i)Object.prototype.hasOwnProperty.call(i,r)&&(e[r]=i[r]);h&&h(n);while(d.length)d.shift()();return c.push.apply(c,l||[]),t()}function t(){for(var e,n=0;n<c.length;n++){for(var t=c[n],r=!0,a=1;a<t.length;a++){var u=t[a];0!==o[u]&&(r=!1)}r&&(c.splice(n--,1),e=i(i.s=t[0]))}return e}var r={},a={app:0},o={app:0},c=[];function u(e){return i.p+"js/"+({}[e]||e)+"."+{"chunk-2686b73e":"5e550651","chunk-2d0de3a1":"78c6a37e","chunk-32d5c273":"1f492da7","chunk-4745e87c":"b0140916","chunk-47fd607a":"f22c280b","chunk-64fa879d":"261a6aaf","chunk-efc6f6ea":"cd8f9d3e"}[e]+".js"}function i(n){if(r[n])return r[n].exports;var t=r[n]={i:n,l:!1,exports:{}};return e[n].call(t.exports,t,t.exports,i),t.l=!0,t.exports}i.e=function(e){var n=[],t={"chunk-2686b73e":1,"chunk-32d5c273":1,"chunk-4745e87c":1,"chunk-47fd607a":1,"chunk-64fa879d":1,"chunk-efc6f6ea":1};a[e]?n.push(a[e]):0!==a[e]&&t[e]&&n.push(a[e]=new Promise((function(n,t){for(var r="css/"+({}[e]||e)+"."+{"chunk-2686b73e":"b178a4e6","chunk-2d0de3a1":"31d6cfe0","chunk-32d5c273":"bc7f83f6","chunk-4745e87c":"13052f82","chunk-47fd607a":"3eb29bf9","chunk-64fa879d":"115291ab","chunk-efc6f6ea":"829b099e"}[e]+".css",o=i.p+r,c=document.getElementsByTagName("link"),u=0;u<c.length;u++){var l=c[u],f=l.getAttribute("data-href")||l.getAttribute("href");if("stylesheet"===l.rel&&(f===r||f===o))return n()}var d=document.getElementsByTagName("style");for(u=0;u<d.length;u++){l=d[u],f=l.getAttribute("data-href");if(f===r||f===o)return n()}var h=document.createElement("link");h.rel="stylesheet",h.type="text/css",h.onload=n,h.onerror=function(n){var r=n&&n.target&&n.target.src||o,c=new Error("Loading CSS chunk "+e+" failed.\n("+r+")");c.code="CSS_CHUNK_LOAD_FAILED",c.request=r,delete a[e],h.parentNode.removeChild(h),t(c)},h.href=o;var p=document.getElementsByTagName("head")[0];p.appendChild(h)})).then((function(){a[e]=0})));var r=o[e];if(0!==r)if(r)n.push(r[2]);else{var c=new Promise((function(n,t){r=o[e]=[n,t]}));n.push(r[2]=c);var l,f=document.createElement("script");f.charset="utf-8",f.timeout=120,i.nc&&f.setAttribute("nonce",i.nc),f.src=u(e);var d=new Error;l=function(n){f.onerror=f.onload=null,clearTimeout(h);var t=o[e];if(0!==t){if(t){var r=n&&("load"===n.type?"missing":n.type),a=n&&n.target&&n.target.src;d.message="Loading chunk "+e+" failed.\n("+r+": "+a+")",d.name="ChunkLoadError",d.type=r,d.request=a,t[1](d)}o[e]=void 0}};var h=setTimeout((function(){l({type:"timeout",target:f})}),12e4);f.onerror=f.onload=l,document.head.appendChild(f)}return Promise.all(n)},i.m=e,i.c=r,i.d=function(e,n,t){i.o(e,n)||Object.defineProperty(e,n,{enumerable:!0,get:t})},i.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},i.t=function(e,n){if(1&n&&(e=i(e)),8&n)return e;if(4&n&&"object"===typeof e&&e&&e.__esModule)return e;var t=Object.create(null);if(i.r(t),Object.defineProperty(t,"default",{enumerable:!0,value:e}),2&n&&"string"!=typeof e)for(var r in e)i.d(t,r,function(n){return e[n]}.bind(null,r));return t},i.n=function(e){var n=e&&e.__esModule?function(){return e["default"]}:function(){return e};return i.d(n,"a",n),n},i.o=function(e,n){return Object.prototype.hasOwnProperty.call(e,n)},i.p="/",i.oe=function(e){throw console.error(e),e};var l=window["webpackJsonp"]=window["webpackJsonp"]||[],f=l.push.bind(l);l.push=n,l=l.slice();for(var d=0;d<l.length;d++)n(l[d]);var h=f;c.push([0,"chunk-vendors"]),t()})({0:function(e,n,t){e.exports=t("56d7")},"3aff":function(e,n,t){},"4c70":function(e,n,t){"use strict";t("b257")},"56d7":function(e,n,t){"use strict";t.r(n);var r=t("2b0e"),a=function(){var e=this,n=e._self._c;return n("div",{attrs:{id:"app"}},[n("router-view")],1)},o=[],c={name:"App",components:{}},u=c,i=(t("4c70"),t("2877")),l=Object(i["a"])(u,a,o,!1,null,null,null),f=l.exports,d=t("8c4f");r["default"].use(d["a"]);const h=[{path:"/",name:"用户登录",component:()=>t.e("chunk-32d5c273").then(t.bind(null,"9ed6"))},{path:"/home",name:"首页",redirect:"/welcome",component:()=>t.e("chunk-47fd607a").then(t.bind(null,"7abe")),children:[{path:"/welcome",name:"欢迎页面",component:()=>t.e("chunk-efc6f6ea").then(t.bind(null,"b9ec"))},{path:"/college",name:"学院管理",component:()=>t.e("chunk-64fa879d").then(t.bind(null,"eb30")),meta:{title:"学院管理"}},{path:"/notice",name:"通知公告",component:()=>t.e("chunk-2686b73e").then(t.bind(null,"ab43")),meta:{title:"学院管理"}},{path:"/users",name:"学院管理",component:()=>t.e("chunk-4745e87c").then(t.bind(null,"4468")),meta:{title:"用户管理"}},{path:"/data",name:"数据分析",component:()=>t.e("chunk-2d0de3a1").then(t.bind(null,"856d")),meta:{title:"数据分析"}}]}],p=new d["a"]({routes:h});var s=p,m=t("cee4"),b=t("313e"),v=t("5c96"),k=t.n(v);t("0fae"),t("3aff");r["default"].prototype.axios=m["a"],m["a"].defaults.baseURL="http://127.0.0.1:8088/match/api",r["default"].prototype.$echarts=b,r["default"].use(k.a),r["default"].config.productionTip=!1,new r["default"]({router:s,render:e=>e(f)}).$mount("#app")},b257:function(e,n,t){}});
//# sourceMappingURL=app.60e47798.js.map