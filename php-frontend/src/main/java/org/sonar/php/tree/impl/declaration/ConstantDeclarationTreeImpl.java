/*
 * SonarQube PHP Plugin
 * Copyright (C) 2010 SonarSource and Akram Ben Aissi
 * sonarqube@googlegroups.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.php.tree.impl.declaration;

import com.google.common.base.Functions;
import com.google.common.collect.Iterators;
import org.sonar.php.tree.impl.PHPTree;
import org.sonar.php.tree.impl.SeparatedList;
import org.sonar.php.tree.impl.lexical.InternalSyntaxToken;
import org.sonar.plugins.php.api.tree.Tree;
import org.sonar.plugins.php.api.tree.declaration.ConstantDeclarationTree;
import org.sonar.plugins.php.api.tree.declaration.VariableDeclarationTree;
import org.sonar.plugins.php.api.tree.lexical.SyntaxToken;
import org.sonar.plugins.php.api.visitors.TreeVisitor;

import java.util.Iterator;

public class ConstantDeclarationTreeImpl extends PHPTree implements ConstantDeclarationTree {

  private final Kind KIND = Kind.CONSTANT_DECLARATION;
  private final SyntaxToken constToken;
  private final SeparatedList<VariableDeclarationTree> declarations;
  private final InternalSyntaxToken eosToken;

  public ConstantDeclarationTreeImpl(
      SyntaxToken constToken,
      SeparatedList<VariableDeclarationTree> declarations,
      InternalSyntaxToken eosToken
  ) {
    this.constToken = constToken;
    this.declarations = declarations;
    this.eosToken = eosToken;
  }

  @Override
  public SyntaxToken constToken() {
    return constToken;
  }

  @Override
  public SeparatedList<VariableDeclarationTree> declarations() {
    return declarations;
  }

  @Override
  public SyntaxToken eosToken() {
    return eosToken;
  }

  @Override
  public Kind getKind() {
    return KIND;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.concat(
      Iterators.singletonIterator(constToken),
      declarations.elementsAndSeparators(Functions.<VariableDeclarationTree>identity()),
      Iterators.singletonIterator(eosToken));
  }

  @Override
  public void accept(TreeVisitor visitor) {
    visitor.visitConstDeclaration(this);
  }

}