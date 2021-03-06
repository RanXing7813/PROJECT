

    var editor = UM.getEditor('container', {
    	
    	
    	toolbarTopOffset  : 100,
        filterRules: function () {
            return{
                span:function(node){
                    if(/Wingdings|Symbol/.test(node.getStyle('font-family'))){
                        return true;
                    }else{
                        node.parentNode.removeChild(node,true)
                    }
                },
                p: function(node){
                    var listTag;
                    if(node.getAttr('class') == 'MsoListParagraph'){
                        listTag = 'MsoListParagraph'
                    }
                    node.setAttr();
                    if(listTag){
                        node.setAttr('class','MsoListParagraph')
                    }
                    if(!node.firstChild()){
                        node.innerHTML(UM.browser.ie ? '&nbsp;' : '<br/>')
                    }
                },
                div: function (node) {
                    var tmpNode, p = UM.uNode.createElement('p');
                    while (tmpNode = node.firstChild()) {
                        if (tmpNode.type == 'text' || !UM.dom.dtd.$block[tmpNode.tagName]) {
                            p.appendChild(tmpNode);
                        } else {
                            if (p.firstChild()) {
                                node.parentNode.insertBefore(p, node);
                                p = UM.uNode.createElement('p');
                            } else {
                                node.parentNode.insertBefore(tmpNode, node);
                            }
                        }
                    }
                    if (p.firstChild()) {
                        node.parentNode.insertBefore(p, node);
                    }
                    node.parentNode.removeChild(node);
                },
                //$:{}表示不保留任何属性
                br: {$: {}},
//                a: function (node) {
//                    if(!node.firstChild()){
//                        node.parentNode.removeChild(node);
//                        return;
//                    }
//                    node.setAttr();
//                    node.setAttr('href', '#')
//                },
//                strong: {$: {}},
//                b:function(node){
//                    node.tagName = 'strong'
//                },
//                i:function(node){
//                    node.tagName = 'em'
//                },
//                em: {$: {}},
//                img: function (node) {
//                    var src = node.getAttr('src');
//                    node.setAttr();
//                    node.setAttr({'src':src})
//                },
                ol:{$: {}},
                ul: {$: {}},

                dl:function(node){
                    node.tagName = 'ul';
                    node.setAttr()
                },
                dt:function(node){
                    node.tagName = 'li';
                    node.setAttr()
                },
                dd:function(node){
                    node.tagName = 'li';
                    node.setAttr()
                },
                li: function (node) {

                    var className = node.getAttr('class');
                    if (!className || !/list\-/.test(className)) {
                        node.setAttr()
                    }
                    var tmpNodes = node.getNodesByTagName('ol ul');
                    UM.utils.each(tmpNodes,function(n){
                        node.parentNode.insertAfter(n,node);

                    })

                },
                table: function (node) {
                    UM.utils.each(node.getNodesByTagName('table'), function (t) {
                        UM.utils.each(t.getNodesByTagName('tr'), function (tr) {
                            var p = UM.uNode.createElement('p'), child, html = [];
                            while (child = tr.firstChild()) {
                                html.push(child.innerHTML());
                                tr.removeChild(child);
                            }
                            p.innerHTML(html.join('&nbsp;&nbsp;'));
                            t.parentNode.insertBefore(p, t);
                        })
                        t.parentNode.removeChild(t)
                    });
                    var val = node.getAttr('width');
                    node.setAttr();
                    if (val) {
                        node.setAttr('width', val);
                    }
                },
                tbody: {$: {}},
                caption: {$: {}},
                th: {$: {}},
                td: {$: {valign: 1, align: 1,rowspan:1,colspan:1,width:1,height:1}},
                tr: {$: {}},
                h3: {$: {}},
                h2: {$: {}},
            	//focus时自动清空初始化时的内容  
                //黑名单，以下标签及其子节点都会被过滤掉
                '-': 'script style meta iframe embed object'
            }
        }()
    });