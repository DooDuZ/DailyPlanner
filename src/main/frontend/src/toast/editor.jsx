import { Editor, EventMap } from '@toast-ui/editor';
import { Component, createRef } from 'react';
import '@toast-ui/editor/dist/i18n/ko-kr';

export default class EditorComponent extends Component<EditorProps & CustomEditorProps> {
  rootEl = createRef();

  getRootElement() {
    return this.rootEl.current;
  }

  getInstance() {
    return this.editorInst;
  }

  getHTML() {
    return this.editorInst.getHTML();
  }

  getBindingEventNames() {
    return Object.keys(this.props)
      .filter((key) => /^on[A-Z][a-zA-Z]+/.test(key))
      .filter((key) => this.props);
  }

  bindEventHandlers(props: EditorProps) {
    this.getBindingEventNames().forEach((key) => {
      const eventName = key[2].toLowerCase() + key.slice(3);

      this.editorInst.off(eventName);
      this.editorInst.on(eventName, props);
    });
  }

  getInitEvents() {
    return this.getBindingEventNames().reduce(
      (acc: Record, key) => {
        const eventName = (key[2].toLowerCase() + key.slice(3));

        acc[eventName] = this.props;

        return acc;
      },
      {}
    );
  }


  componentDidMount() {
    const { projectId, uploadFiles, ...options } = this.props;

    const abortController = new AbortController();

    this.editorInst = new Editor({
      el: this.rootEl.current,
      language: 'ko',
      toolbarItems: [['heading', 'bold', 'italic'], ['hr'], ['ul', 'ol', 'task', 'indent', 'outdent'],['scrollSync']],
      hideModeSwitch: true,
      previewHighlight: true,
      autofocus: false,
      initialEditType: 'markdown',
      previewStyle: 'vertical',
      minHeight: '200px',
      height: '400px',
      initialValue : ' '
      // height: 'auto',
    });
  }

  shouldComponentUpdate(nextProps: EditorProps) {
    const instance = this.getInstance();
    const { height, previewStyle } = nextProps;

    if (height && this.props.height !== height) {
      instance.setHeight(height);
    }

    if (previewStyle && this.props.previewStyle !== previewStyle) {
      instance.changePreviewStyle(previewStyle);
    }

    this.bindEventHandlers(nextProps);

    return false;
  }

  render() {
    return <div ref={this.rootEl} />;
  }
}
