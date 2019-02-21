﻿/*
    Validform version 5.3
	By sean during April 7, 2010 - December 28, 2012
	For more information, please visit http://validform.rjboy.cn
	Validform is available under the terms of the MIT license.
 */

(function(d, f, b) {
	var g = null, j = null, i = true;
	var e = {
		tit : "提示信息",
		w : {
			"*" : "不能为空！",
			"q" : "请填写正确金额,是10位整数,精确到4位小数！",
			"*6-16" : "请填写6到16位任意字符！",
			"n" : "请填写数字！",
			"n6-16" : "请填写6到16位数字！",
			"Zn" : "请输入中文！",
			"s" : "不能输入特殊字符！",
			"s6-18" : "请填写6到18位字符！",
			"p" : "请填写邮政编码！",
			"m" : "请填写手机号码！",
			"e" : "邮箱地址格式不对！",
			"url" : "请填写网址！",
			"idcard" : "请输入正确的省份证号！"
		},
		def : "请填写正确信息！",
		undef : "datatype未定义！",
		reck : "两次输入的内容不一致！",
		r : "通过信息验证！",
		c : "正在检测信息…",
		s : "请填入信息！",
		s_auto : "请{填写}{0}！",
		v : "所填信息没有经过验证，请稍后…",
		p : "正在提交数据…"
	};
	d.Tipmsg = e;
	var a = function(l, n, k) {
		var n = d.extend({}, a.defaults, n);
		n.datatype && d.extend(a.util.dataType, n.datatype);
		var m = this;
		m.tipmsg = {
			w : {}
		};
		m.settings = n;
		m.forms = l;
		m.objects = [];
		if (k === true) {
			return false
		}
		l.each(function() {
			if (this.validform_inited == "inited") {
				return true
			}
			this.validform_inited = "inited";
			var o = d(this);
			this.validform_status = "normal";
			o.data("tipmsg", m.tipmsg);
			o.find("[datatype]").live("blur", function() {
				var p = arguments[1];
				a.util.check.call(this, o, m, p)
			});
			a.util.enhance.call(o, n.tiptype, n.usePlugin, n.tipSweep);
			n.btnSubmit && o.find(n.btnSubmit).bind("click", function() {
				o.trigger("submit");
				return false
			});
			o.submit(function() {
				var p = a.util.submitForm.call(o, n);
				p === b && (p = true);
				return p
			});
			o.find("[type='reset']").add(o.find(n.btnReset)).bind("click",
					function() {
						a.util.resetForm.call(o)
					})
		});
		if (n.tiptype == 1 || (n.tiptype == 2 || n.tiptype == 3) && n.ajaxPost) {
			c()
		}
	};
	a.defaults = {
		tiptype : 1,
		tipSweep : false,
		showAllError : false,
		postonce : false,
		ajaxPost : false
	};
	a.util = {
		dataType : {
			"q" : /^(-)?(([1-9]{1}(\d){0,10})|([0]{1}))(\.(\d){1,4})?$/,
			"*" : /[\w\W]+/,
			"*6-16" : /^[\w\W]{6,16}$/,
			n : /^\d{1,14}$/,
			"Zn" : /^[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*$/,
			"n6-16" : /^\d{6,16}$/,
			s : /^[\u4E00-\u9FA5\uf900-\ufa2d\w\.\s]+$/,
			"s6-18" : /^[\u4E00-\u9FA5\uf900-\ufa2d\w\.\s]{6,18}$/,
			p : /^[0-9]{6}$/,
			m : /(^(\d{11})$|^((\d{7,8})$|^(\d{4}|\d{3})-(\d{7,8})$|^(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})$|^(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})$))/,
			e : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			url : /^(\w+:\/\/)?\w+(\.\w+)+.*$/,
			idcard : idcardFun
		},
		toString : Object.prototype.toString,
		getValue : function(m) {
			var l, k = this;
			if (m.is(":radio")) {
				l = k.find(":radio[name='" + m.attr("name") + "']:checked")
						.val();
				l = l === b ? "" : l
			} else {
				if (m.is(":checkbox")) {
					l = "";
					k.find(":checkbox[name='" + m.attr("name") + "']:checked")
							.each(function() {
								l += d(this).val() + ","
							});
					l = l === b ? "" : l
				} else {
					l = m.val()
				}
			}
			return d.trim(l)
		},
		isEmpty : function(k) {
			return k === "" || k === d.trim(this.attr("tip"))
		},
		enhance : function(l, m, n, k) {
			var o = this;
			o
					.find("[datatype]")
					.each(
							function() {
								if (l == 2) {
									if (d(this).parent().next().find(
											".Validform_checktip").length == 0) {
										d(this)
												.parent()
												.next()
												.append(
														"<span class='Validform_checktip' />")
									}
								} else {
									if (l == 3 || l == 4) {
										if (d(this).siblings(
												".Validform_checktip").length == 0) {
											d(this)
													.parent()
													.append(
															"<span class='Validform_checktip' />")
										}
									}
								}
							});
			o.find("input[recheck]").each(
					function() {
						if (this.validform_inited == "inited") {
							return true
						}
						this.validform_inited = "inited";
						var q = d(this);
						var p = o.find("input[name='" + d(this).attr("recheck")
								+ "']");
						p.bind("keyup", function() {
							if (p.val() == q.val() && p.val() != "") {
								if (p.attr("tip")) {
									if (p.attr("tip") == p.val()) {
										return false
									}
								}
								q.trigger("blur")
							}
						}).bind("blur", function() {
							if (p.val() != q.val() && q.val() != "") {
								if (q.attr("tip")) {
									if (q.attr("tip") == q.val()) {
										return false
									}
								}
								q.trigger("blur")
							}
						})
					});
			o.find("[tip]").each(function() {
				if (this.validform_inited == "inited") {
					return true
				}
				this.validform_inited = "inited";
				var q = d(this).attr("tip");
				var p = d(this).attr("altercss");
				d(this).focus(function() {
					if (d(this).val() == q) {
						d(this).val("");
						if (p) {
							d(this).removeClass(p)
						}
					}
				}).blur(function() {
					if (d.trim(d(this).val()) === "") {
						d(this).val(q);
						if (p) {
							d(this).addClass(p)
						}
					}
				})
			});
			o.find(":checkbox[datatype],:radio[datatype]").each(
					function() {
						if (this.validform_inited == "inited") {
							return true
						}
						this.validform_inited = "inited";
						var q = d(this);
						var p = q.attr("name");
						o.find("[name='" + p + "']").filter(":checkbox,:radio")
								.bind("click", function() {
									setTimeout(function() {
										q.trigger("blur")
									}, 0)
								})
					});
			o.find("select[datatype][multiple]").bind("click", function() {
				var p = d(this);
				setTimeout(function() {
					p.trigger("blur")
				}, 0)
			});
			a.util.usePlugin.call(o, m, l, n, k)
		},
		usePlugin : function(o, l, n, r) {
			var s = this, o = o || {};
			if (s.find("input[plugin='swfupload']").length
					&& typeof (swfuploadhandler) != "undefined") {
				var k = {
					custom_settings : {
						form : s,
						showmsg : function(v, t, u) {
							a.util.showmsg.call(s, v, l, {
								obj : s.find("input[plugin='swfupload']"),
								type : t,
								sweep : n
							})
						}
					}
				};
				k = d.extend(true, {}, o.swfupload, k);
				s.find("input[plugin='swfupload']").each(function(t) {
					if (this.validform_inited == "inited") {
						return true
					}
					this.validform_inited = "inited";
					d(this).val("");
					swfuploadhandler.init(k, t)
				})
			}
			if (s.find("input[plugin='datepicker']").length && d.fn.datePicker) {
				o.datepicker = o.datepicker || {};
				if (o.datepicker.format) {
					Date.format = o.datepicker.format;
					delete o.datepicker.format
				}
				if (o.datepicker.firstDayOfWeek) {
					Date.firstDayOfWeek = o.datepicker.firstDayOfWeek;
					delete o.datepicker.firstDayOfWeek
				}
				s
						.find("input[plugin='datepicker']")
						.each(
								function(t) {
									if (this.validform_inited == "inited") {
										return true
									}
									this.validform_inited = "inited";
									o.datepicker.callback
											&& d(this)
													.bind(
															"dateSelected",
															function() {
																var u = new Date(
																		d.event._dpCache[this._dpId]
																				.getSelected()[0])
																		.asString(Date.format);
																o.datepicker
																		.callback(
																				u,
																				this)
															});
									d(this).datePicker(o.datepicker)
								})
			}
			if (s.find("input[plugin*='passwordStrength']").length
					&& d.fn.passwordStrength) {
				o.passwordstrength = o.passwordstrength || {};
				o.passwordstrength.showmsg = function(u, v, t) {
					a.util.showmsg.call(s, v, l, {
						obj : u,
						type : t,
						sweep : n
					})
				};
				s.find("input[plugin='passwordStrength']").each(function(t) {
					if (this.validform_inited == "inited") {
						return true
					}
					this.validform_inited = "inited";
					d(this).passwordStrength(o.passwordstrength)
				})
			}
			if (r != "addRule" && o.jqtransform && d.fn.jqTransSelect) {
				var m = function(t) {
					var u = d(".jqTransformSelectWrapper ul:visible");
					u.each(function() {
						var v = d(this).parents(
								".jqTransformSelectWrapper:first").find(
								"select").get(0);
						if (!(t && v.oLabel && v.oLabel.get(0) == t.get(0))) {
							d(this).hide()
						}
					})
				};
				var p = function(t) {
					if (d(t.target).parents(".jqTransformSelectWrapper").length === 0) {
						m(d(t.target))
					}
				};
				var q = function() {
					d(document).mousedown(p)
				};
				if (o.jqtransform.selector) {
					s.find(o.jqtransform.selector).filter(
							'input:submit, input:reset, input[type="button"]')
							.jqTransInputButton();
					s.find(o.jqtransform.selector).filter(
							"input:text, input:password").jqTransInputText();
					s.find(o.jqtransform.selector).filter("input:checkbox")
							.jqTransCheckBox();
					s.find(o.jqtransform.selector).filter("input:radio")
							.jqTransRadio();
					s.find(o.jqtransform.selector).filter("textarea")
							.jqTransTextarea();
					if (s.find(o.jqtransform.selector).filter("select").length > 0) {
						s.find(o.jqtransform.selector).filter("select")
								.jqTransSelect();
						q()
					}
				} else {
					s.jqTransform()
				}
				s.find(".jqTransformSelectWrapper").find("li a").click(
						function() {
							d(this).parents(".jqTransformSelectWrapper").find(
									"select").trigger("blur")
						})
			}
		},
		getNullmsg : function(o) {
			var n = this;
			var m = /[\u4E00-\u9FA5\uf900-\ufa2da-zA-Z\s]+/g;
			var k;
			var l = n.siblings(".Validform_label").text()
					|| n.siblings().find(".Validform_label").text()
					|| n.parent().siblings(".Validform_label").text()
					|| n.parent().siblings().find(".Validform_label").text();
			l = l.match(m);
			if (l) {
				l = l[0].replace(/\s(?![a-zA-Z])/g, "");
				k = e.s_auto.replace(/\{0\}/, l);
				if (n.attr("recheck")) {
					k = k.replace(/\{(.+)\}/, "")
				} else {
					k = k.replace(/\{(.+)\}/, "$1")
				}
			} else {
				k = o.data("tipmsg").s || e.s
			}
			n.attr("nullmsg", k);
			return k
		},
		getErrormsg : function(s, o, u) {
			var n = this;
			var p = /^(.+?)((\d+)-(\d+))?$/, m = /^(.+?)(\d+)-(\d+)$/, l = /(.*?)\d+(.+?)\d+(.*)/, q = o
					.match(p), t, r;
			if (u == "recheck") {
				r = s.data("tipmsg").reck || e.reck;
				return r
			}
			if (q[0] in e.w) {
				return s.data("tipmsg").w[q[0]] || e.w[q[0]]
			}
			for ( var k in e.w) {
				if (k.indexOf(q[1]) != -1 && m.test(k)) {
					r = (s.data("tipmsg").w[k] || e.w[k]).replace(l, "$1"
							+ q[3] + "$2" + q[4] + "$3");
					e.w[q[0]] = r;
					return r
				}
			}
			return s.data("tipmsg").def || e.def
		},
		_regcheck : function(t, n, u, A) {
			var A = A, y = null, v = false, o = /\/.+\//g, k = /^(.+?)(\d+)-(\d+)$/, l = 3;
			if (o.test(t)) {
				var s = t.match(o)[0].slice(1, -1);
				var r = t.replace(o, "");
				var q = RegExp(s, r);
				v = q.test(n)
			} else {
				if (a.util.toString.call(a.util.dataType[t]) == "[object Function]") {
					v = a.util.dataType[t](n, u, A, a.util.dataType);
					if (v === true || v === b) {
						v = true
					} else {
						y = v;
						v = false
					}
				} else {
					if (!(t in a.util.dataType)) {
						var m = t.match(k), z;
						if (!m) {
							v = false;
							y = A.data("tipmsg").undef || e.undef
						} else {
							for ( var B in a.util.dataType) {
								z = B.match(k);
								if (!z) {
									continue
								}
								if (m[1] === z[1]) {
									var w = a.util.dataType[B].toString(), r = w
											.match(/\/[mgi]*/g)[1].replace("/",
											""), x = new RegExp("\\{" + z[2]
											+ "," + z[3] + "\\}", "g");
									w = w.replace(/\/[mgi]*/g, "/").replace(x,
											"{" + m[2] + "," + m[3] + "}")
											.replace(/^\//, "").replace(/\/$/,
													"");
									a.util.dataType[t] = new RegExp(w, r);
									break
								}
							}
						}
					}
					if (a.util.toString.call(a.util.dataType[t]) == "[object RegExp]") {
						v = a.util.dataType[t].test(n)
					}
				}
			}
			if (v) {
				l = 2;
				y = u.attr("sucmsg") || A.data("tipmsg").r || e.r;
				if (u.attr("recheck")) {
					var p = A.find("input[name='" + u.attr("recheck")
							+ "']:first");
					if (n != p.val()) {
						v = false;
						l = 3;
						y = u.attr("errormsg")
								|| a.util.getErrormsg.call(u, A, t, "recheck")
					}
				}
			} else {
				y = y || u.attr("errormsg") || a.util.getErrormsg.call(u, A, t);
				if (a.util.isEmpty.call(u, n)) {
					y = u.attr("nullmsg") || a.util.getNullmsg.call(u, A)
				}
			}
			return {
				passed : v,
				type : l,
				info : y
			}
		},
		regcheck : function(n, s, m) {
			var t = this, k = null, l = false, r = 3;
			if (m.attr("ignore") === "ignore" && a.util.isEmpty.call(m, s)) {
				if (m.data("cked")) {
					k = ""
				}
				return {
					passed : true,
					type : 4,
					info : k
				}
			}
			m.data("cked", "cked");
			var u = a.util.parseDatatype(n);
			var q;
			for (var p = 0; p < u.length; p++) {
				for (var o = 0; o < u[p].length; o++) {
					q = a.util._regcheck(u[p][o], s, m, t);
					if (!q.passed) {
						break
					}
				}
				if (q.passed) {
					break
				}
			}
			return q
		},
		parseDatatype : function(r) {
			var q = /\/.+?\/[mgi]*(?=(,|$|\||\s))|[\w\*-]+/g, o = r.match(q), p = r
					.replace(q, "").replace(/\s*/g, "").split(""), l = [], k = 0;
			l[0] = [];
			l[0].push(o[0]);
			for (var s = 0; s < p.length; s++) {
				if (p[s] == "|") {
					k++;
					l[k] = []
				}
				l[k].push(o[s + 1])
			}
			return l
		},
		showmsg : function(n, l, m, k) {
			if (n == b) {
				return
			}
			if (k == "bycheck" && m.sweep && m.obj
					&& !m.obj.is(".Validform_error")) {
				return
			}
			d.extend(m, {
				curform : this
			});
			if (typeof l == "function") {
				l(n, m, a.util.cssctl);
				return
			}
			if (l == 1 || k == "byajax" && l != 4) {
				j.find(".Validform_info").html(n)
			}
			if (l == 1 && k != "bycheck" && m.type != 2 || k == "byajax"
					&& l != 4) {
				i = false;
				j.find(".iframe").css("height", j.outerHeight());
				j.show();
				h(j, 100)
			}
			if (l == 2 && m.obj) {
				m.obj.parent().next().find(".Validform_checktip").html(n);
				a.util.cssctl(
						m.obj.parent().next().find(".Validform_checktip"),
						m.type)
			}
			if ((l == 3 || l == 4) && m.obj) {
				m.obj.siblings(".Validform_checktip").html(n);
				a.util.cssctl(m.obj.siblings(".Validform_checktip"), m.type)
			}
		},
		cssctl : function(l, k) {
			switch (k) {
			case 1:
				l.removeClass("Validform_right Validform_wrong").addClass(
						"Validform_checktip Validform_loading");
				break;
			case 2:
				l.removeClass("Validform_wrong Validform_loading").addClass(
						"Validform_checktip Validform_right");
				break;
			case 4:
				l.removeClass(
						"Validform_right Validform_wrong Validform_loading")
						.addClass("Validform_checktip");
				break;
			default:
				l.removeClass("Validform_right Validform_loading").addClass(
						"Validform_checktip Validform_wrong")
			}
		},
		getQuery : function(l, k) {
			var m = l.indexOf("?");
			if (m != -1 && !k) {
				return {
					query : l.substring(m + 1) + "&",
					url : l.substring(0, m)
				}
			} else {
				return {
					query : "",
					url : l
				}
			}
		},
		check : function(w, r, u, n) {
			var o = r.settings;
			var u = u || "";
			var k = a.util.getValue.call(w, d(this));
			if (o.ignoreHidden && d(this).is(":hidden")
					|| d(this).data("dataIgnore") === "dataIgnore") {
				return true
			}
			if (o.dragonfly && !d(this).data("cked")
					&& a.util.isEmpty.call(d(this), k)
					&& d(this).attr("ignore") != "ignore") {
				return false
			}
			var t = a.util.regcheck.call(w, d(this).attr("datatype"), k,
					d(this));
			if (k == this.validform_lastval && !d(this).attr("recheck")
					&& u == "") {
				return t.passed ? true : false
			}
			this.validform_lastval = k;
			var s;
			g = s = d(this);
			if (!t.passed) {
				a.util.abort.call(s[0]);
				if (!n) {
					a.util.showmsg.call(w, t.info, o.tiptype, {
						obj : d(this),
						type : t.type,
						sweep : o.tipSweep
					}, "bycheck");
					!o.tipSweep && s.addClass("Validform_error")
				}
				return false
			}
			if (d(this).attr("ajaxurl") && !a.util.isEmpty.call(d(this), k)
					&& !n) {
				var m = d(this);
				if (u == "postform") {
					m[0].validform_subpost = "postform"
				} else {
					m[0].validform_subpost = ""
				}
				if (m[0].validform_valid === "posting"
						&& k == m[0].validform_ckvalue) {
					return "ajax"
				}
				m[0].validform_valid = "posting";
				m[0].validform_ckvalue = k;
				a.util.showmsg.call(w, r.tipmsg.c || e.c, o.tiptype, {
					obj : m,
					type : 1,
					sweep : o.tipSweep
				}, "bycheck");
				a.util.abort.call(s[0]);
				var v = w[0].validform_config || {};
				v = v.ajaxurl || {};
				var q = a.util.getQuery.call(f, m.attr("ajaxurl"), /get/i
						.test(v.type));
				var p = {
					type : "POST",
					cache : false,
					url : q.url,
					data : q.query + "param=" + encodeURIComponent(k)
							+ "&name="
							+ encodeURIComponent(d(this).attr("name")),
					success : function(y) {
						if (y.status === "y") {
							m[0].validform_valid = "true";
							y.info && m.attr("sucmsg", y.info);
							a.util.showmsg.call(w, m.attr("sucmsg")
									|| r.tipmsg.r || e.r, o.tiptype, {
								obj : m,
								type : 2,
								sweep : o.tipSweep
							}, "bycheck");
							s.removeClass("Validform_error");
							g = null;
							if (m[0].validform_subpost == "postform") {
								w.trigger("submit")
							}
						} else {
							m[0].validform_valid = y.info;
							a.util.showmsg.call(w, y.info, o.tiptype, {
								obj : m,
								type : 3,
								sweep : o.tipSweep
							});
							s.addClass("Validform_error")
						}
						s[0].validform_ajax = null
					},
					error : function(y) {
						if (y.statusText == "parsererror") {
							if (y.responseText == "y") {
								v.success({
									status : "y"
								})
							} else {
								v.success({
									status : "n",
									info : y.responseText
								})
							}
							return false
						}
						if (y.statusText !== "abort") {
							var z = "status: " + y.status + "; statusText: "
									+ y.statusText;
							a.util.showmsg.call(w, z, o.tiptype, {
								obj : m,
								type : 3,
								sweep : o.tipSweep
							});
							s.addClass("Validform_error")
						}
						m[0].validform_valid = y.statusText;
						s[0].validform_ajax = null;
						return true
					}
				};
				if (v.success) {
					var x = v.success;
					v.success = function(y) {
						p.success(y);
						x(y, m)
					}
				}
				if (v.error) {
					var l = v.error;
					v.error = function(y) {
						p.error(y) && l(y, m)
					}
				}
				v = d.extend({}, p, v, {
					dataType : "json"
				});
				s[0].validform_ajax = d.ajax(v);
				return "ajax"
			} else {
				if (d(this).attr("ajaxurl") && a.util.isEmpty.call(d(this), k)) {
					a.util.abort.call(s[0]);
					s[0].validform_valid = "true"
				}
			}
			if (!n) {
				a.util.showmsg.call(w, t.info, o.tiptype, {
					obj : d(this),
					type : t.type,
					sweep : o.tipSweep
				}, "bycheck");
				s.removeClass("Validform_error")
			}
			g = null;
			return true
		},
		submitForm : function(p, l, k, s, u) {
			var x = this;
			if (x[0].validform_status === "posting") {
				return false
			}
			if (p.postonce && x[0].validform_status === "posted") {
				return false
			}
			var w = p.beforeCheck && p.beforeCheck(x);
			if (w === false) {
				return false
			}
			var t = true, o;
			x
					.find("[datatype]")
					.each(
							function() {
								if (l) {
									return false
								}
								if (p.ignoreHidden
										&& d(this).is(":hidden")
										|| d(this).data("dataIgnore") === "dataIgnore") {
									return true
								}
								var A = a.util.getValue.call(x, d(this)), B;
								g = B = d(this);
								o = a.util.regcheck.call(x, d(this).attr(
										"datatype"), A, d(this));
								if (!o.passed) {
									a.util.showmsg.call(x, o.info, p.tiptype, {
										obj : d(this),
										type : o.type,
										sweep : p.tipSweep
									});
									B.addClass("Validform_error");
									if (!p.showAllError) {
										B.focus();
										t = false;
										return false
									}
									t && (t = false);
									return true
								}
								if (d(this).attr("ajaxurl")
										&& !a.util.isEmpty.call(d(this), A)) {
									if (this.validform_valid !== "true") {
										var z = d(this);
										a.util.showmsg.call(x,
												x.data("tipmsg").v || e.v,
												p.tiptype, {
													obj : z,
													type : 3,
													sweep : p.tipSweep
												});
										B.addClass("Validform_error");
										setTimeout(function() {
											z.trigger("blur", [ "postform" ])
										}, 1500);
										if (!p.showAllError) {
											t = false;
											return false
										}
										t && (t = false);
										return true
									}
								} else {
									if (d(this).attr("ajaxurl")
											&& a.util.isEmpty.call(d(this), A)) {
										a.util.abort.call(this);
										this.validform_valid = "true"
									}
								}
								a.util.showmsg.call(x, o.info, p.tiptype, {
									obj : d(this),
									type : o.type,
									sweep : p.tipSweep
								});
								B.removeClass("Validform_error");
								g = null
							});
			if (p.showAllError) {
				x.find(".Validform_error:first").focus()
			}
			if (t) {
				var r = p.beforeSubmit && p.beforeSubmit(x);
				if (r === false) {
					return false
				}
				x[0].validform_status = "posting";
				var m = x[0].validform_config || {};
				if (p.ajaxPost || s === "ajaxPost") {
					var v = m.ajaxpost || {};
					v.url = k || v.url || m.url || x.attr("action");
					v.success
							|| v.error
							|| a.util.showmsg.call(x,
									x.data("tipmsg").p || e.p, p.tiptype, {
										obj : x,
										type : 1,
										sweep : p.tipSweep
									}, "byajax");
					if (u) {
						v.async = false
					} else {
						if (u === false) {
							v.async = true
						}
					}
					if (v.success) {
						var y = v.success;
						v.success = function(z) {
							p.callback && p.callback(z);
							x[0].validform_ajax = null;
							if (z.status === "y") {
								x[0].validform_status = "posted"
							} else {
								x[0].validform_status = "normal"
							}
							y(z, x)
						}
					}
					if (v.error) {
						var n = v.error;
						v.error = function(z) {
							p.callback && p.callback(z);
							x[0].validform_status = "normal";
							x[0].validform_ajax = null;
							n(z, x)
						}
					}
					var q = {
						type : "POST",
						async : true,
						data : x.serializeArray(),
						success : function(z) {
							if (z.status === "y") {
								x[0].validform_status = "posted";
								a.util.showmsg.call(x, z.info, p.tiptype, {
									obj : x,
									type : 2,
									sweep : p.tipSweep
								}, "byajax")
							} else {
								x[0].validform_status = "normal";
								a.util.showmsg.call(x, z.info, p.tiptype, {
									obj : x,
									type : 3,
									sweep : p.tipSweep
								}, "byajax")
							}
							p.callback && p.callback(z);
							x[0].validform_ajax = null
						},
						error : function(z) {
							var A = "status: " + z.status + "; statusText: "
									+ z.statusText;
							a.util.showmsg.call(x, A, p.tiptype, {
								obj : x,
								type : 3,
								sweep : p.tipSweep
							}, "byajax");
							p.callback && p.callback(z);
							x[0].validform_status = "normal";
							x[0].validform_ajax = null
						}
					};
					v = d.extend({}, q, v, {
						dataType : "json"
					});
					x[0].validform_ajax = d.ajax(v)
				} else {
					if (!p.postonce) {
						x[0].validform_status = "normal"
					}
					var k = k || m.url;
					if (k) {
						x.attr("action", k)
					}
					return p.callback && p.callback(x)
				}
			}
			return false
		},
		resetForm : function() {
			var k = this;
			k.each(function() {
				this.reset();
				this.validform_status = "normal"
			});
			k.find(".Validform_right").text("");
			k.find(".passwordStrength").children().removeClass("bgStrength");
			k.find(".Validform_checktip").removeClass(
					"Validform_wrong Validform_right Validform_loading");
			k.find(".Validform_error").removeClass("Validform_error");
			k.find("[datatype]").removeData("cked").removeData("dataIgnore");
			k.eq(0).find("input:first").focus()
		},
		abort : function() {
			if (this.validform_ajax) {
				this.validform_ajax.abort()
			}
		}
	};
	d.Datatype = a.util.dataType;
	a.prototype = {
		dataType : a.util.dataType,
		eq : function(l) {
			var k = this;
			if (l >= k.forms.length) {
				return null
			}
			if (!(l in k.objects)) {
				k.objects[l] = new a(d(k.forms[l]).get(), k.settings, true)
			}
			return k.objects[l]
		},
		resetStatus : function() {
			var k = this;
			d(k.forms).each(function() {
				this.validform_status = "normal"
			});
			return this
		},
		setStatus : function(k) {
			var l = this;
			d(l.forms).each(function() {
				this.validform_status = k || "posting"
			});
			return this
		},
		getStatus : function() {
			var l = this;
			var k = d(l.forms)[0].validform_status;
			return k
		},
		ignore : function(k) {
			var l = this;
			var k = k || "[datatype]";
			d(l.forms).find(k).each(
					function() {
						d(this).data("dataIgnore", "dataIgnore").removeClass(
								"Validform_error")
					});
			return this
		},
		unignore : function(k) {
			var l = this;
			var k = k || "[datatype]";
			d(l.forms).find(k).each(function() {
				d(this).removeData("dataIgnore")
			});
			return this
		},
		addRule : function(n) {
			var m = this;
			var n = n || [];
			for (var l = 0; l < n.length; l++) {
				var p = d(m.forms).find(n[l].ele);
				for ( var k in n[l]) {
					k !== "ele" && p.attr(k, n[l][k])
				}
			}
			d(m.forms).each(
					function() {
						var o = d(this);
						a.util.enhance.call(o, m.settings.tiptype,
								m.settings.usePlugin, m.settings.tipSweep,
								"addRule")
					});
			return this
		},
		ajaxPost : function(k, m, l) {
			var n = this;
			if (n.settings.tiptype == 1 || n.settings.tiptype == 2
					|| n.settings.tiptype == 3) {
				c()
			}
			a.util.submitForm.call(d(n.forms[0]), n.settings, k, l, "ajaxPost",
					m);
			return this
		},
		submitForm : function(k, l) {
			var n = this;
			var m = a.util.submitForm.call(d(n.forms[0]), n.settings, k, l);
			m === b && (m = true);
			if (m === true) {
				n.forms[0].submit()
			}
			return this
		},
		resetForm : function() {
			var k = this;
			a.util.resetForm.call(d(k.forms));
			return this
		},
		abort : function() {
			var k = this;
			d(k.forms).each(function() {
				a.util.abort.call(this)
			});
			return this
		},
		check : function(m, k) {
			var k = k || "[datatype]", o = this, n = d(o.forms), l = true;
			n.find(k).each(function() {
				a.util.check.call(this, n, o, "", m) || (l = false)
			});
			return l
		},
		config : function(k) {
			var l = this;
			k = k || {};
			d(l.forms).each(
					function() {
						this.validform_config = d.extend(true,
								this.validform_config, k)
					});
			return this
		}
	};
	d.fn.Validform = function(k) {
		return new a(this, k)
	};
	function h(n, m) {
		var l = (d(window).width() - n.outerWidth()) / 2, k = (d(window)
				.height() - n.outerHeight()) / 2, k = (document.documentElement.scrollTop ? document.documentElement.scrollTop
				: document.body.scrollTop)
				+ (k > 0 ? k : 0);
		n.css({
			left : l
		}).animate({
			top : k
		}, {
			duration : m,
			queue : false
		})
	}
	function c() {
		if (d("#Validform_msg").length !== 0) {
			return false
		}
		j = d(
				'<div id="Validform_msg"><div class="Validform_title">'
						+ e.tit
						+ '<a class="Validform_close" href="javascript:void(0);">&chi;</a></div><div class="Validform_info"></div><div class="iframe"><iframe frameborder="0" scrolling="no" height="100%" width="100%"></iframe></div></div>')
				.appendTo("body");
		j.find("a.Validform_close").click(function() {
			j.hide();
			i = true;
			if (g) {
				g.focus().addClass("Validform_error")
			}
			return false
		}).focus(function() {
			this.blur()
		});
		d(window).bind("scroll resize", function() {
			!i && h(j, 400)
		})
	}
	d.Showmsg = function(k) {
		c();
		a.util.showmsg.call(f, k, 1, {})
	};
	d.Hidemsg = function() {
		j.hide();
		i = true
	}
	//xie 2016/08/03 扩展身份证验证dataType idcard 校验函数 
	function idcardFun(gets,obj,curform,datatype){
	    //该方法由佚名网友提供;
	    var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子;
	    var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值，10代表X;
	   
	    if (gets.length == 15) {   
	     return isValidityBrithBy15IdCard(gets);   
	    }else if (gets.length == 18){   
	     var a_idCard = gets.split("");// 得到身份证数组   
	     if (isValidityBrithBy18IdCard(gets)&&isTrueValidateCodeBy18IdCard(a_idCard)) {   
	      return true;   
	     }   
	     return false;
	    }
	    return false;
	    
	    function isTrueValidateCodeBy18IdCard(a_idCard) {   
	     var sum = 0; // 声明加权求和变量   
	     if (a_idCard[17].toLowerCase() == 'x') {   
	      a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作   
	     }   
	     for ( var i = 0; i < 17; i++) {   
	      sum += Wi[i] * a_idCard[i];// 加权求和   
	     }   
	     valCodePosition = sum % 11;// 得到验证码所位置   
	     if (a_idCard[17] == ValideCode[valCodePosition]) {   
	      return true;   
	     }
	     return false;   
	    }
	    
	    function isValidityBrithBy18IdCard(idCard18){   
	     var year = idCard18.substring(6,10);   
	     var month = idCard18.substring(10,12);   
	     var day = idCard18.substring(12,14);   
	     var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
	     // 这里用getFullYear()获取年份，避免千年虫问题   
	     if(temp_date.getFullYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){   
	      return false;   
	     }
	     return true;   
	    }
	    
	    function isValidityBrithBy15IdCard(idCard15){   
	     var year =  idCard15.substring(6,8);   
	     var month = idCard15.substring(8,10);   
	     var day = idCard15.substring(10,12);
	     var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
	     //对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
	     if(temp_date.getYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){   
	      return false;   
	     }
	     return true;
	    }
	   }
})(jQuery, window);